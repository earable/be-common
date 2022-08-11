package ai.earable.platform.common.utils.caller;

import reactor.core.publisher.Mono;

/**
 * Created by BinhNH on 21/07/2022
 */
public interface FmsCaller {
    Mono<Boolean> validateFeature(String token, String featureName);
    Mono<Boolean> validateProfileId(String token, String userId, String profileId);
}
