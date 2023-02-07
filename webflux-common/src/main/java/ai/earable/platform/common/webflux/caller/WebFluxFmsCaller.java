package ai.earable.platform.common.webflux.caller;

import ai.earable.platform.common.data.exception.EarableErrorCode;
import ai.earable.platform.common.data.exception.EarableException;
import ai.earable.platform.common.utils.caller.Caller;
import ai.earable.platform.common.utils.caller.FmsCaller;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class WebFluxFmsCaller implements FmsCaller {
    final String FMS_API_IS_ACTIVE_FEATURE = "/feature/{featureName}/is-active";

    @Autowired
    private Caller caller;

    @Value("${earable.fms.host:}")
    private String fmsHost;

    private static final List<String> cachingActivatedFeatures = Arrays.asList("FOCUS", "NAP", "SLEEP", "RECOVERY");

    private Mono<String> checkFeatureIsActive(String token, String featureName) {
        String path = fmsHost + FMS_API_IS_ACTIVE_FEATURE;
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);
        return caller.getMono(path, String.class, headers, featureName)
                .onErrorResume(throwable -> {
                    log.error("Validate featureName failed with message {}", throwable.getLocalizedMessage());
                    return Mono.error(new EarableException(HttpStatus.BAD_REQUEST.value(), EarableErrorCode.INTERNAL_SERVER_ERROR));
                }).doOnSuccess(s -> log.trace("[CONFIGURATION SERVICE] -> Call Feature Service successfully"));
    }

    public Mono<Boolean> validateFeature(String token, String featureName){
        if(featureName == null || featureName.isBlank()){
            log.error("Validate featureName failed with error code {}", EarableErrorCode.FEATURE_NAME_NOT_NULL_OR_BLANK);
            return Mono.error(new EarableException(HttpStatus.BAD_REQUEST.value(), EarableErrorCode.FEATURE_NAME_NOT_NULL_OR_BLANK));
        }

        if(cachingActivatedFeatures.contains(featureName.toUpperCase()))
            return Mono.just(true);

        return checkFeatureIsActive(token, featureName.toUpperCase()).flatMap(s -> {
            JSONObject object = null;
            try {
                object = new JSONObject(s);
                if (null != object.get("active") && object.get("active").equals("true")) {
                    log.trace("Feature name is activated");
                    return Mono.just(true);
                } else {
                    log.error("Validate featureName:wq failed with error code {}", EarableErrorCode.FEATURE_WAS_NOT_ACTIVATED);
                    return Mono.error(new EarableException(HttpStatus.BAD_REQUEST.value(), EarableErrorCode.FEATURE_WAS_NOT_ACTIVATED, featureName));
                }
            } catch (JSONException e) {
                log.error("Validate featureName failed with message {}", e.getLocalizedMessage());
                return Mono.error(new EarableException(HttpStatus.BAD_REQUEST.value(), EarableErrorCode.FEATURE_WAS_NOT_ACTIVATED, featureName));
            }
        });
    }

    @Override
    public Mono<Boolean> validateProfileId(String token, String userId, String profileId) {
        //TODO: call to FMS to validate
        if(!userId.equals(profileId))
            return Mono.error(new EarableException(HttpStatus.FORBIDDEN.value(), EarableErrorCode.INPUT_INVALID, "profileId"));
        return Mono.just(true);
    }
}
