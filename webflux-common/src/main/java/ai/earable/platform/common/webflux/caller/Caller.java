package ai.earable.platform.common.webflux.caller;

import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Created by BinhNH on 3/30/2022
 */
public interface Caller {
    <T, V> Mono<V> requestToMono(HttpMethod method, String calledUri, T requestBody, Class<T> requestType, Class<V> responseType);
    <T, V> Mono<V> requestToMono(HttpMethod method, String calledUri, T requestBody, Class<T> requestType, Class<V> responseType, String... params);
    <T, V> Mono<V> requestToMono(HttpMethod method, String calledUri, MultiValueMap<String, T> multiValueMap, Class<V> responseType, String... params);
    <V> Flux<V> getFlux(HttpMethod method, String calledUri, Class<V> responseType);
    <V> Flux<V> getFlux(HttpMethod method, String calledUri, Class<V> responseType, String... params);
    <V> Flux<V> getFlux(HttpMethod method, String calledUri, Class<V> responseType, Map<String, String> headers,
                        String... params);
    <V> Mono<V> getMono(String calledUriTemplate, Class<V> responseType);
    <V> Mono<V> getMono(String calledUriTemplate, Class<V> responseType, String... params);
    <V> Mono<V> getMono(String calledUriTemplate, Class<V> responseType, MultiValueMap<String, String> multiValueMap, String... params);
}
