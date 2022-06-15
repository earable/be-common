package ai.earable.platform.common.exception;

import lombok.Getter;

/**
 * Created by BinhNH on 3/19/22
 */
@Getter
public enum EarableErrorCode {
    FEATURE_NAME_NULL_OR_BLANK ("FEATURE_NAME_NULL_OR_BLANK"),
    DEVICE_ID_NULL_OR_BLANK ("DEVICE_ID_NULL_OR_BLANK"),
    SESSION_ID_NULL_OR_BLANK ("SESSION_ID_NULL_OR_BLANK"),
    PROFILE_ID_NULL_OR_BLANK ("PROFILE_ID_NULL_OR_BLANK"),
    METRIC_NAME_NULL_OR_BLANK ("METRIC_NAME_NULL_OR_BLANK"),

    //For writing request
    MONITORED_DATA_LIST_NULL_OR_EMPTY ("MONITORED_DATA_LIST_NULL_OR_EMPTY"),
    MONITORED_DATA_VALUE_LIST_NULL_OR_EMPTY ("MONITORED_DATA_VALUE_LIST_NULL_OR_EMPTY"),

    SIZE_LIMIT_INVALID ("SIZE_LIMIT_INVALID"),
    PARAM_PAGING_INVALID ("PARAM_PAGING_INVALID"),

    DURATION_INVALID ("DURATION_INVALID"),

    INPUT_INVALID ("INPUT_INVALID"),

    INTERNAL_SERVER_ERROR ("INTERNAL_SERVER_ERROR");

    private final String errorDetail;

    EarableErrorCode(String errorDetail) {
        this.errorDetail = errorDetail;
    }
}
