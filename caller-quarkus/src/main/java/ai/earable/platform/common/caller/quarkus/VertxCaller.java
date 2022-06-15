package ai.earable.platform.common.caller.quarkus;

import ai.earable.platform.common.exception.EarableErrorCode;
import ai.earable.platform.common.exception.EarableException;
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

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Map;

/**
 * Created by BinhNH on 3/18/22
 */
@Slf4j
public final class VertxCaller {
    private static final long DEFAULT_TIME_OUT = 10000; //TODO: Move to config map

    public static <V> Mono<V> get(WebClient webClient, URI uriTemplate, Class<V> responseType){
        return get(webClient, uriTemplate, responseType, DEFAULT_TIME_OUT);
    }

    public static <V> Mono<V> get(WebClient webClient, URI uriTemplate, Class<V> responseType, long timeout){
        HttpRequest<V> request = webClient.getAbs(uriTemplate.toString())
            .timeout(timeout).expect(responsePredicate())
            .as(bodyCodec(responseType));
        return request.send().flatMap(VertxCaller::responseToBody).convert().with(UniReactorConverters.toMono())
                .doOnError(throwable -> log.error(throwable.getLocalizedMessage()));
    }

    public static <V> Mono<V> get(WebClient webClient, String uriTemplate, Map<String, String> queryParams,
                                  Class<V> responseType, String... pathParams){
        return get(webClient, uriTemplate, queryParams, responseType, DEFAULT_TIME_OUT, pathParams);
    }

    private static URI setPathParams(String template, String... pathParams){
        UriBuilder builder = UriBuilder.fromUri(template);
        return builder.build(pathParams);
    }

    private static HttpRequest<Buffer> setQueryParams(HttpRequest<Buffer> request, Map<String, String> queryParams){
        if(queryParams != null && queryParams.size() > 0){
            queryParams.forEach((key, value) -> {
                if(key != null && !key.trim().equals(""))
                    if(value != null && !value.trim().equals(""))
                        request.addQueryParam(key, value);
            });
        }
        return request;
    }

    public static <V> Mono<V> get(WebClient webClient, String uriTemplate, Map<String, String> queryParams,
                                  Class<V> responseType, long timeout, String... pathParams){
        String absUri = setPathParams(uriTemplate, pathParams).toString();
        MultiMap multiMap = MultiMap.caseInsensitiveMultiMap().addAll(queryParams);
        HttpRequest<Buffer> requestBuffer = webClient.getAbs(absUri);
        HttpRequest<V> request = setQueryParams(requestBuffer, queryParams).putHeaders(multiMap)
            .timeout(timeout).expect(responsePredicate())
            .as(bodyCodec(responseType));
        return request.send().flatMap(VertxCaller::responseToBody).convert().with(UniReactorConverters.toMono())
                .doOnError(throwable -> log.error(throwable.getLocalizedMessage()));
    }


    public static <T, V> Mono<V> post(WebClient webClient, String calledUri, T requestBody, Class<T> requestType, Class<V> responseType){
        HttpRequest<V> request = webClient.postAbs(calledUri)
            .timeout(DEFAULT_TIME_OUT).expect(responsePredicate())
            .as(bodyCodec(responseType));
        return request.sendBuffer(convertToBuffer(requestBody, requestType))
                .flatMap(VertxCaller::responseToBody)
                .convert().with(UniReactorConverters.toMono())
                .doOnError(throwable -> log.error(throwable.getLocalizedMessage()));
    }

    private static ResponsePredicate responsePredicate(){
        ErrorConverter errorConverter = ErrorConverter.createFullBody(responsePredicateResult ->
            convertToMyException(responsePredicateResult.response()));
        return ResponsePredicate.create(ResponsePredicate.status(200, 399), errorConverter);
    }

    private static <V> BodyCodec<V> bodyCodec(Class<V> responseType){
        return responseType.equals(String.class) ? (BodyCodec<V>) BodyCodec.string() : BodyCodec.json(responseType);
    }

    private static <V> Uni<V> responseToBody(HttpResponse<V> response){
        return Uni.createFrom().item(response.body());
    }

    private URI convertFrom(String template, String... params){
        return UriBuilder.fromUri(template).build(params);
    }

    private static <T> Buffer convertToBuffer(T requestBody, Class<T> requestType){
        if(requestType.equals(String.class))
            return Buffer.buffer((String) requestBody);

        try {
            return Buffer.buffer(JsonUtil.convertObjectToBytes(requestBody));
        } catch (JsonProcessingException e) {
            throw new EarableException(500, EarableErrorCode.INTERNAL_SERVER_ERROR.getErrorDetail(),
                "Failed to convert requestBody to buffer with message "+e.getLocalizedMessage());
        }
    }

    private static <V> EarableException convertToMyException(HttpResponse<V> response){
        return convertToMyException(response, EarableErrorCode.INTERNAL_SERVER_ERROR);
    }

    private static <V> EarableException convertToMyException(HttpResponse<V> response, EarableErrorCode earableErrorCode){
        V v = response.body();
        log.error("Rest api calling failed with error-code {} and error body: {}", response.statusCode(), v);
        if(v != null &&  v.toString() != null && !v.toString().isEmpty()){
            return new EarableException(response.statusCode(), earableErrorCode.getErrorDetail(), response.bodyAsString());
        }
        return new EarableException(response.statusCode(), earableErrorCode.getErrorDetail(), response.statusMessage());
    }
}