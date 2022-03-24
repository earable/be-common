package ai.earable.platform.common.exception.exception;

import lombok.Getter;

/**
 * Created by BinhNH on 3/19/22
 */
@Getter
public enum EarableErrorCode {
    FEATURE_NAME_NULL_OR_BLANK ("The feature name can't be null or blank!"),
    DEVICE_ID_NULL_OR_BLANK ("The device id can't be null or blank!"),
    SESSION_ID_NULL_OR_BLANK ("The session id can't be null or blank!"),
    PROFILE_ID_NULL_OR_BLANK ("The profile id can't be null or blank!"),
    METRIC_NAME_NULL_OR_BLANK ("The metric name can't be null or blank!"),

    //For writing request
    MONITORED_DATA_LIST_NULL_OR_EMPTY ("Can't write without or null data!"),
    MONITORED_DATA_VALUE_LIST_NULL_OR_EMPTY ("Can't write without or null data!"),

    INTERNAL_SERVER_ERROR ("Something went wrong!");

    private final String errorDetail;

    EarableErrorCode(String errorDetail) {
        this.errorDetail = errorDetail;
    }
}
