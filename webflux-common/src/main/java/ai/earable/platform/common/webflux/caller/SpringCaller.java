package ai.earable.platform.common.webflux.caller;

import ai.earable.platform.common.data.http.HttpMethod;
import ai.earable.platform.common.utils.caller.Caller;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Created by BinhNH on 21/07/2022
 */
public interface SpringCaller extends Caller {
    <V> Mono<V> requestToMono(HttpMethod method, String uri, Map<String, String> headers, MultipartBodyBuilder requestBody, Class<V> responseType, String... pathParams);
    <V> Mono<V> getMono(String uri, Class<V> responseType, MultiValueMap<String, String> queryParams, String... pathParams);
    <V> Mono<ClientResponse> requestToClientResponse(HttpMethod method, String uri, String bearerToken, String... pathParams);
    <T, V> Mono<V> requestToMono(HttpMethod method, String uri, MultiValueMap<String, T> multipartData, Class<V> responseType, String... pathParams);
    <V> Flux<V> requestToFlux(HttpMethod method, String uri, String bearerToken, MultiValueMap<String, String> queryParams, Class<V> responseType);
}
