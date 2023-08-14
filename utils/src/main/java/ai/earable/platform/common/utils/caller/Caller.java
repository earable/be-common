package ai.earable.platform.common.utils.caller;

import ai.earable.platform.common.data.http.HttpMethod;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

/**
 * Created by BinhNH on 3/30/2022
 */
public interface Caller {
    <V> Mono<V> getMono(String uri, Class<V> responseType);
    <V> Mono<V> getMono(String uri, Class<V> responseType, String... pathParams);
    <V> Mono<V> getMono(String uri, Class<V> responseType, Map<String, String> headers, String... pathParams);

    <V> Flux<V> getFlux(HttpMethod method, String uri, Class<V> responseType);
    <V> Flux<V> getFlux(HttpMethod method, String uri, Class<V> responseType, String... pathParams);
    <V> Flux<V> getFlux(HttpMethod method, String uri, Class<V> responseType, Map<String, String> headers, String... pathParams);

    <T, V> Mono<V> requestToMono(HttpMethod method, String uri, T requestBody, Class<T> requestType, Class<V> responseType);
    <T, V> Mono<V> requestToMono(HttpMethod method, String uri, T requestBody, Class<T> requestType, Class<V> responseType, String... pathParams);
    <T, V> Mono<V> requestToMono(HttpMethod method, String uri, String bearerToken, T requestBody, Class<T> requestType, Class<V> responseType);
    <V> Mono<V> requestToMono(HttpMethod method, String uri, String bearerToken, Class<V> responseType, String... pathParams);
    <T, V> Mono<V> requestToMono(HttpMethod method, String uri, String bearerToken, T requestBody, Class<V> responseType, String... pathParams);
    <T, V> Mono<V> requestToMonoCustom(HttpMethod method, String uri, String bearerToken, T requestBody, Class<V> responseType, String... pathParams);
    <V> Flux<V> requestToFlux(HttpMethod method, String uri, String bearerToken, Class<V> responseType, String... pathParams);
    <T, V> Flux<V> requestToFlux(HttpMethod method, String uri, String bearerToken, List<T> requestBody, Class<V> responseType);
}
