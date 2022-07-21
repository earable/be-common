package ai.earable.platform.common.quarkus.caller;

import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;

/**
 * Created by BinhNH on 21/07/2022
 */
public interface QuarkusCaller {
    <V> Mono<V> get(URI uriTemplate, Class<V> responseType);
    <V> Mono<V> get(URI uriTemplate, Class<V> responseType, long timeout);
    <V> Mono<V> get(String uriTemplate, Map<String, String> queryParams, Class<V> responseType, String... pathParams);
}
