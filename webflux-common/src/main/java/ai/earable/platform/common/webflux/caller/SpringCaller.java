package ai.earable.platform.common.webflux.caller;

import ai.earable.platform.common.data.http.HttpMethod;
import ai.earable.platform.common.utils.caller.Caller;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Created by BinhNH on 21/07/2022
 */
public interface SpringCaller extends Caller {
    <V> Mono<V> requestToMono(HttpMethod method, String calledUri, Map<String, String> headers, MultipartBodyBuilder requestBody, Class<V> responseType);
    <V> Mono<ClientResponse> requestToClientResponse(HttpMethod method, String calledUri, String bearerToken, String... params);
}
