package ai.earable.platform.common.quarkus.caller;

import ai.earable.platform.common.data.exception.EarableErrorCode;
import ai.earable.platform.common.data.exception.EarableException;
import ai.earable.platform.common.utils.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import reactor.core.publisher.Mono;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Map;

/**
 * Created by BinhNH on 21/07/2022
 */
@ApplicationScoped
@Slf4j
public class VertxCaller implements QuarkusCaller {
    private static final long DEFAULT_TIME_OUT = 30000; //TODO: Move to config map

    @Inject
    protected WebClient webClient;

    public <V> Mono<V> get(URI uriTemplate, Class<V> responseType){
        return get(uriTemplate, responseType, DEFAULT_TIME_OUT);
    }

    public <V> Mono<V> get(URI uriTemplate, Class<V> responseType, long timeout){
        HttpRequest<V> request = webClient.getAbs(uriTemplate.toString())
            .timeout(timeout).expect(responsePredicate())
            .as(bodyCodec(responseType));
        return request.send().flatMap(this::responseToBody).convert().with(UniReactorConverters.toMono())
                .doOnError(throwable -> log.error(throwable.getLocalizedMessage()));
    }

    public <V> Mono<V> get(String uriTemplate, Map<String, String> queryParams,
                           Class<V> responseType, String... pathParams){
        return get(uriTemplate, queryParams, responseType, DEFAULT_TIME_OUT, pathParams);
    }

    public <V> Mono<V> get(String uriTemplate, Map<String, String> queryParams,
                           Class<V> responseType, long timeout, String... pathParams){
        String absUri = setPathParams(uriTemplate, pathParams).toString();
        MultiMap multiMap = MultiMap.caseInsensitiveMultiMap().addAll(queryParams);
        HttpRequest<Buffer> requestBuffer = webClient.getAbs(absUri);
        HttpRequest<V> request = setQueryParams(requestBuffer, queryParams).putHeaders(multiMap)
            .timeout(timeout).expect(responsePredicate())
            .as(bodyCodec(responseType));
        return request.send().flatMap(this::responseToBody).convert().with(UniReactorConverters.toMono())
                .doOnError(throwable -> log.error(throwable.getLocalizedMessage()));
    }

    public <T, V> Mono<V> post(String calledUri, T requestBody, Class<T> requestType, Class<V> responseType){
        HttpRequest<V> request = webClient.postAbs(calledUri)
            .timeout(DEFAULT_TIME_OUT).expect(responsePredicate())
            .as(bodyCodec(responseType));
        return request.sendBuffer(convertToBuffer(requestBody, requestType))
                .flatMap(this::responseToBody)
                .convert().with(UniReactorConverters.toMono())
                .doOnError(throwable -> log.error(throwable.getLocalizedMessage()));
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
        return convertToMyException(response, EarableErrorCode.INTERNAL_SERVER_ERROR);
    }

    private <V> EarableException convertToMyException(HttpResponse<V> response, EarableErrorCode earableErrorCode){
        V v = response.body();
        log.error("Rest api calling failed with error-code {} and error body: {}", response.statusCode(), v);
        if(v != null &&  v.toString() != null && !v.toString().isEmpty()){
            return new EarableException(response.statusCode(), earableErrorCode.getErrorDetail(), response.bodyAsString());
        }
        return new EarableException(response.statusCode(), earableErrorCode.getErrorDetail(), response.statusMessage());
    }
}
