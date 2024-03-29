package ai.earable.platform.common.quarkus.caller;

import ai.earable.platform.common.data.exception.EarableErrorCode;
import ai.earable.platform.common.data.exception.EarableException;
import ai.earable.platform.common.data.exception.ErrorDetails;
import ai.earable.platform.common.data.http.HttpMethod;
import ai.earable.platform.common.utils.JsonUtil;
import ai.earable.platform.common.utils.caller.Caller;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.netty.handler.timeout.TimeoutException;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.converters.uni.UniReactorConverters;
import io.vertx.mutiny.core.MultiMap;
import io.vertx.mutiny.core.buffer.Buffer;
import io.vertx.mutiny.ext.web.client.HttpRequest;
import io.vertx.mutiny.ext.web.client.HttpResponse;
import io.vertx.mutiny.ext.web.client.WebClient;
import io.vertx.mutiny.ext.web.client.predicate.ErrorConverter;
import io.vertx.mutiny.ext.web.client.predicate.ResponsePredicate;
import io.vertx.mutiny.ext.web.codec.BodyCodec;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * Created by BinhNH on 21/07/2022
 */
@ApplicationScoped
@Slf4j
public class VertxCaller implements Caller {
    @ConfigProperty(name = "earable.internal.caller.timeout", defaultValue="15")
    private int defaultTimeout;

    @ConfigProperty(name = "earable.internal.caller.retry.times", defaultValue="3")
    private int defaultRetryTimes;

    @ConfigProperty(name = "earable.internal.caller.retry.delay", defaultValue="1")
    private int defaultRetryDelay;

    @Inject
    protected WebClient webClient;

    public <V> Mono<V> getMono(String uri, Class<V> responseType){
        return getMono(uri, responseType, defaultTimeout);
    }

    private <V> Mono<V> getMono(String uri, Class<V> responseType, long timeout){
        HttpRequest<V> request = webClient.getAbs(uri)
            .timeout(timeout*1000L).expect(responsePredicate())
            .as(bodyCodec(responseType));
        return request.send().flatMap(this::responseToBody).convert().with(UniReactorConverters.toMono())
                .retryWhen(configRetry(HttpMethod.GET, uri, defaultRetryTimes, defaultRetryDelay))
                .doOnError(throwable -> log.error(throwable.getLocalizedMessage()));
    }

    @Override
    public <V> Mono<V> getMono(String uri, Class<V> responseType, String... pathParams) {
        return Mono.error(new EarableException(500, EarableErrorCode.INTERNAL_SERVER_ERROR.getErrorDetail(), "Unsupported method!"));
    }

    public <V> Mono<V> getMono(String uri, Class<V> responseType, Map<String, String> queryParams, String... pathParams){
        return getMono(uri, queryParams, responseType, defaultTimeout, pathParams);
    }

    @Override
    public <V> Flux<V> getFlux(HttpMethod method, String uri, Class<V> responseType) {
        return Flux.error(new EarableException(500, EarableErrorCode.INTERNAL_SERVER_ERROR.getErrorDetail(), "Unsupported method!"));
    }

    @Override
    public <V> Flux<V> getFlux(HttpMethod method, String uri, Class<V> responseType, String... pathParams) {
        return Flux.error(new EarableException(500, EarableErrorCode.INTERNAL_SERVER_ERROR.getErrorDetail(), "Unsupported method!"));
    }

    @Override
    public <V> Flux<V> getFlux(HttpMethod method, String uri, Class<V> responseType, Map<String, String> headers, String... pathParams) {
        return Flux.error(new EarableException(500, EarableErrorCode.INTERNAL_SERVER_ERROR.getErrorDetail(), "Unsupported method!"));
    }

    private <V> Mono<V> getMono(String uri, Map<String, String> queryParams, Class<V> responseType, long timeout, String... pathParams){
        String absUri = setPathParams(uri, pathParams).toString();
        MultiMap multiMap = MultiMap.caseInsensitiveMultiMap().addAll(queryParams);
        HttpRequest<Buffer> requestBuffer = webClient.getAbs(absUri);
        HttpRequest<V> request = setQueryParams(requestBuffer, queryParams).putHeaders(multiMap)
            .timeout(timeout*1000L).expect(responsePredicate())
            .as(bodyCodec(responseType));
        return request.send().flatMap(this::responseToBody).convert().with(UniReactorConverters.toMono())
                .retryWhen(configRetry(HttpMethod.GET, uri, defaultRetryTimes, defaultRetryDelay))
                .doOnError(throwable -> log.error(throwable.getLocalizedMessage()));
    }

    public <T, V> Mono<V> requestToMono(HttpMethod method, String uri, T requestBody, Class<T> requestType, Class<V> responseType){
        HttpRequest<V> request = webClient.postAbs(uri)
            .timeout(defaultTimeout* 1000L).expect(responsePredicate())
            .as(bodyCodec(responseType));
        return request.sendBuffer(convertToBuffer(requestBody, requestType))
                .flatMap(this::responseToBody)
                .convert().with(UniReactorConverters.toMono())
                .retryWhen(configRetry(method, uri, defaultRetryTimes, defaultRetryDelay))
                .doOnError(throwable -> log.error(throwable.getLocalizedMessage()));
    }

    @Override
    public <T, V> Mono<V> requestToMono(HttpMethod method, String uri, T requestBody, Class<T> requestType, Class<V> responseType, String... pathParams) {
        return Mono.error(new EarableException(500, EarableErrorCode.INTERNAL_SERVER_ERROR.getErrorDetail(), "Unsupported method!"));
    }

    @Override
    public <T, V> Mono<V> requestToMono(HttpMethod method, String uri, String token, T requestBody, Class<T> requestType, Class<V> responseType) {
        return Mono.error(new EarableException(500, EarableErrorCode.INTERNAL_SERVER_ERROR.getErrorDetail(), "Unsupported method!"));
    }

    @Override
    public <V> Mono<V> requestToMono(HttpMethod method, String uri, String bearerToken, Class<V> responseType, String... pathParams) {
        return Mono.error(new EarableException(500, EarableErrorCode.INTERNAL_SERVER_ERROR.getErrorDetail(), "Unsupported method!"));
    }

    @Override
    public <T, V> Mono<V> requestToMono(HttpMethod method, String uri, String bearerToken, T requestBody, Class<V> responseType, String... pathParams) {
        return Mono.error(new EarableException(500, EarableErrorCode.INTERNAL_SERVER_ERROR.getErrorDetail(), "Unsupported method!"));
    }

    @Override
    public <T, V> Mono<V> requestToMonoCustom(HttpMethod method, String uri, String bearerToken, T requestBody, Class<V> responseType, String... pathParams) {
        return null;
    }

    @Override
    public <V> Flux<V> requestToFlux(HttpMethod method, String uri, String bearerToken, Class<V> responseType, String... pathParams) {
        return Flux.error(new EarableException(500, EarableErrorCode.INTERNAL_SERVER_ERROR.getErrorDetail(), "Unsupported method!"));
    }

    @Override
    public <T, V> Flux<V> requestToFlux(HttpMethod method, String uri, String token, List<T> requestBody, Class<V> responseType) {
        return Flux.error(new EarableException(500, EarableErrorCode.INTERNAL_SERVER_ERROR.getErrorDetail(), "Unsupported method!"));
    }

    private static URI setPathParams(String template, String... pathParams){
        UriBuilder builder = UriBuilder.fromUri(template);
        return builder.build(pathParams);
    }

    private HttpRequest<Buffer> setQueryParams(HttpRequest<Buffer> request, Map<String, String> queryParams){
        if(queryParams != null && queryParams.size() > 0){
            queryParams.forEach((key, value) -> {
                if(key != null && !key.trim().equals(""))
                    if(value != null && !value.trim().equals(""))
                        request.addQueryParam(key, value);
            });
        }
        return request;
    }

    private ResponsePredicate responsePredicate(){
        ErrorConverter errorConverter = ErrorConverter.createFullBody(responsePredicateResult ->
            convertToMyException(responsePredicateResult.response()));
        return ResponsePredicate.create(ResponsePredicate.status(200, 399), errorConverter);
    }

    private <V> BodyCodec<V> bodyCodec(Class<V> responseType){
        return responseType.equals(String.class) ? (BodyCodec<V>) BodyCodec.string() : BodyCodec.json(responseType);
    }

    private <V> Uni<V> responseToBody(HttpResponse<V> response){
        return Uni.createFrom().item(response.body());
    }

    private URI convertFrom(String template, String... params){
        return UriBuilder.fromUri(template).build(params);
    }

    private <T> Buffer convertToBuffer(T requestBody, Class<T> requestType){
        if(requestType.equals(String.class))
            return Buffer.buffer((String) requestBody);

        try {
            return Buffer.buffer(JsonUtil.convertObjectToBytes(requestBody));
        } catch (JsonProcessingException e) {
            throw new EarableException(500, EarableErrorCode.INTERNAL_SERVER_ERROR,
                    "Failed to convert requestBody to buffer with message "+e.getLocalizedMessage());
        }
    }

    private <V> EarableException convertToMyException(HttpResponse<V> response){
        V v = response.body();
        String message = String.format("Rest api calling failed with error-code {} and error body: {}", response.statusCode(), v);
        log.error(message);
        if(v != null &&  v.toString() != null && !v.toString().isEmpty()){
            if(response.getHeader("content-type") != null && response.getHeader("content-type").equals(MediaType.APPLICATION_JSON)){
                ErrorDetails errorDetails;
                try {
                    errorDetails = response.bodyAsJson(ErrorDetails.class);
                    return new EarableException(errorDetails.getHttpStatusCode(), errorDetails.getEarableErrorCode(), errorDetails.getDetails());
                }
                catch(Exception ex){
                    log.warn("Something went wrong when cast body to ErrorDetails with message {}", ex.getLocalizedMessage());
                }
            }
            return new EarableException(response.statusCode(), EarableErrorCode.INTERNAL_SERVER_ERROR.getErrorDetail(), response.bodyAsString());
        }
        return new EarableException(response.statusCode(), EarableErrorCode.INTERNAL_SERVER_ERROR.getErrorDetail(), response.statusMessage());
    }

    private static Retry configRetry(HttpMethod httpMethod, String uri, int numberOfRetries, int retryDelayInSecond){
        return Retry.fixedDelay(numberOfRetries, Duration.ofSeconds(retryDelayInSecond))
                .filter(VertxCaller::needToRetry)
                .doAfterRetry(retrySignal -> log.warn("Retry {} request to {} in {}/{} because of {}", httpMethod, uri,
                    retrySignal.totalRetriesInARow()+1, numberOfRetries, retrySignal.failure().getLocalizedMessage()))
                .onRetryExhaustedThrow(((retryBackoffSpec, retrySignal) -> retrySignal.failure()));
    }

    private static boolean needToRetry(Throwable throwable){
        if(throwable.getCause() instanceof TimeoutException)
            return true;

        if(throwable.getCause() instanceof java.util.concurrent.TimeoutException)
            return true;

        final String errorMess = throwable.getLocalizedMessage() == null ?
                throwable.getMessage() : throwable.getLocalizedMessage();
        if(errorMess != null){
            return errorMess.toLowerCase().contains("connection reset by peer")
                    || errorMess.toLowerCase().contains("connection refused")
                    || errorMess.toLowerCase().contains("connection timed out")
                    || errorMess.toLowerCase().contains("connection timeout")
                    || errorMess.toLowerCase().contains("not observe any item or terminal signal within");
        }
        return false;
    }
}
