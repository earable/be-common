package ai.earable.platform.common.webflux.caller;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

/**
 * Created by BinhNH on 3/30/2022
 */
public interface Caller {
    <T, V> Mono<V> requestToMono(HttpMethod method, String calledUri, T requestBody, Class<T> requestType, Class<V> responseType);
    <T, V> Mono<V> requestToMono(HttpMethod method, String calledUri, T requestBody, Class<T> requestType, Class<V> responseType, String... params);
    <T, V> Mono<V> requestToMono(HttpMethod method, String calledUri, MultiValueMap<String, T> multiValueMap, Class<V> responseType, String... params);
    <T, V> Mono<V> requestToMono(HttpMethod method, String calledUri, String token, T requestBody, Class<T> requestType, Class<V> responseType);
    <V> Mono<V> requestToMono(HttpMethod method, String calledUri, Map<String, String> headers, MultipartBodyBuilder requestBody, Class<V> responseType);

    <V> Flux<V> getFlux(HttpMethod method, String calledUri, Class<V> responseType);
    <V> Flux<V> getFlux(HttpMethod method, String calledUri, Class<V> responseType, String... params);
    <V> Flux<V> getFlux(HttpMethod method, String calledUri, Class<V> responseType, Map<String, String> headers, String... params);
    <V> Mono<V> getMono(String calledUriTemplate, Class<V> responseType);
    <V> Mono<V> getMono(String calledUriTemplate, Class<V> responseType, String... params);
    <V> Mono<V> getMono(String calledUriTemplate, Class<V> responseType, Map<String, String> headers, String... params);
    <V> Mono<V> getMono(String calledUriTemplate, Class<V> responseType, MultiValueMap<String, String> multiValueMap, String... params);

    <V> Mono<V> requestToMono(HttpMethod method, String calledUri, String bearerToken, Class<V> responseType, String... params);
    <V> Flux<V> requestToFlux(HttpMethod method, String calledUri, String bearerToken, Class<V> responseType, String... params);
    <T, V> Flux<V> requestToFlux(HttpMethod method, String calledUri, String token, List<T> requestBody, Class<V> responseType);
    <V> Mono<ClientResponse> requestToClientResponse(HttpMethod method, String calledUri, String bearerToken, String... params);
}
