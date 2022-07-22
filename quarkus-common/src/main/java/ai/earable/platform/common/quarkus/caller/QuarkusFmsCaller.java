package ai.earable.platform.common.quarkus.caller;

import ai.earable.platform.common.data.exception.EarableErrorCode;
import ai.earable.platform.common.data.exception.EarableException;
import ai.earable.platform.common.utils.caller.Caller;
import ai.earable.platform.common.utils.caller.FmsCaller;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.json.JSONException;
import org.json.JSONObject;
import reactor.core.publisher.Mono;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by BinhNH on 22/07/2022
 */
@Slf4j
@ApplicationScoped
public class QuarkusFmsCaller implements FmsCaller {
    final String FMS_API_IS_ACTIVE_FEATURE = "/feature/{featureName}/is-active";
    private static final List<String> cachingActivatedFeatures = Arrays.asList("FOCUS", "NAP", "SLEEP", "RECOVERY");

    @Inject
    private Caller caller;

    @ConfigProperty(name = "earable.fms.host")
    private String fmsHost;

    @Override
    public Mono<Boolean> validateFeature(String token, String featureName) {
        if(featureName == null || featureName.isBlank()){
            log.error("Validate featureName failed with error code {}", EarableErrorCode.FEATURE_NAME_NOT_NULL_OR_BLANK);
            return Mono.error(new EarableException(Response.Status.BAD_REQUEST.getStatusCode(),
                    EarableErrorCode.FEATURE_NAME_NOT_NULL_OR_BLANK.getErrorDetail(),
                    EarableErrorCode.FEATURE_NAME_NOT_NULL_OR_BLANK.getErrorDetail()));
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
                    return Mono.error(new EarableException(Response.Status.BAD_REQUEST.getStatusCode(),
                            EarableErrorCode.FEATURE_WAS_NOT_ACTIVATED.getErrorDetail(),
                            EarableErrorCode.FEATURE_WAS_NOT_ACTIVATED.getErrorDetail()));
                }
            } catch (JSONException e) {
                log.error("Validate featureName failed with message {}", e.getLocalizedMessage());
                return Mono.error(new EarableException(Response.Status.BAD_REQUEST.getStatusCode(),
                        EarableErrorCode.FEATURE_WAS_NOT_ACTIVATED.getErrorDetail(),
                        EarableErrorCode.FEATURE_WAS_NOT_ACTIVATED.getErrorDetail()));
            }
        });
    }

    @Override
    public Mono<Boolean> validateProfileId(String token, String userId, String profileId) {
        //TODO: call to FMS to validate
        if(!userId.equals(profileId)){
            log.error("The input profileId is not belong to userId in token!");
            return Mono.error(new EarableException(Response.Status.FORBIDDEN.getStatusCode(),
                    EarableErrorCode.INPUT_INVALID.getErrorDetail(), "The input profileId is not belong to userId in token!"));
        }
        return Mono.just(true);
    }

    private Mono<String> checkFeatureIsActive(String token, String featureName) {
        String path = fmsHost + FMS_API_IS_ACTIVE_FEATURE;
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);
        return caller.getMono(path, String.class, headers, featureName)
                .onErrorResume(throwable -> {
                    String errorMess = String.format("[FMS_CALLER] - Validate featureName failed with message %s", throwable.getLocalizedMessage());
                    log.error(errorMess);
                    return Mono.error(new EarableException(Response.Status.BAD_REQUEST.getStatusCode(),
                            EarableErrorCode.INTERNAL_SERVER_ERROR.getErrorDetail(), errorMess));
                }).doOnSuccess(s -> log.trace("[CONFIGURATION SERVICE] -> Call Feature Service successfully"));
    }
}
