package ai.earable.platform.common.data.exception;

import lombok.Getter;

/**
 * Created by BinhNH on 3/19/22
 */
@Getter
public enum EarableErrorCode {
    FEATURE_NAME_NULL_OR_BLANK ("FEATURE_NAME_NULL_OR_BLANK"),
    FEATURE_WAS_NOT_ACTIVATED ("FEATURE_WAS_NOT_ACTIVATED"),
    FEATURE_WAS_EXISTED ("FEATURE_WAS_EXISTED"),

    DEVICE_ID_NULL_OR_BLANK ("DEVICE_ID_NULL_OR_BLANK"),
    SESSION_ID_NULL_OR_BLANK ("SESSION_ID_NULL_OR_BLANK"),
    PROFILE_ID_NULL_OR_BLANK ("PROFILE_ID_NULL_OR_BLANK"),
    METRIC_NAME_NULL_OR_BLANK ("METRIC_NAME_NULL_OR_BLANK"),
    DOWNLOAD_TIMES_EXCEEDING("DOWNLOAD_TIMES_EXCEEDING"),
    SCHEDULE_NOT_FOUND("SCHEDULE_NOT_FOUND"),

    //For writing request
    MONITORED_DATA_LIST_NULL_OR_EMPTY ("MONITORED_DATA_LIST_NULL_OR_EMPTY"),
    MONITORED_DATA_VALUE_LIST_NULL_OR_EMPTY ("MONITORED_DATA_VALUE_LIST_NULL_OR_EMPTY"),

    SIZE_LIMIT_INVALID ("SIZE_LIMIT_INVALID"),

    DURATION_INVALID ("DURATION_INVALID"),

    INPUT_INVALID ("INPUT_INVALID"),

    PARAM_REQUIRED ("PARAM_REQUIRED"),
    PARAM_INVALID ("PARAM_INVALID"),

    UUID_INVALID ("UUID_INVALID"),
    URL_INVALID ("URL_INVALID"),

    LIST_GENRE_OF_MUSIC_NOT_NULL_OR_BLANK("LIST_GENRE_OF_MUSIC_NOT_NULL_OR_BLANK"),
    LIST_AFFECTATION_NOT_NULL_OR_BLANK("LIST_AFFECTATION_NOT_NULL_OR_BLANK"),
    LIST_HEALTH_GOAL_NOT_NULL_OR_BLANK("LIST_HEALTH_GOAL_NOT_NULL_OR_BLANK"),
    FEATURE_NAME_NOT_NULL_OR_BLANK("FEATURE_NAME_NOT_NULL_OR_BLANK"),
    FEATURE_NAME_NULL_OR_NOT_ACTIVE("FEATURE_NAME_NULL_OR_NOT_ACTIVE"),
    PROFILE_ID_NOT_NULL_OR_BLANK("PROFILE_ID_NOT_NULL_OR_BLANK"),

    SCHEDULE_NAME_IS_EXIST("SCHEDULE_NAME_IS_EXIST"),
    SCHEDULE_NAME_NOT_EXIST("SCHEDULE_NAME_NOT_EXIST"),
    PROFILE_HAVE_NOT_SCHEDULE("PROFILE_HAVE_NOT_SCHEDULE"),
    SCHEDULE_IS_OVERLAPPING("SCHEDULE_IS_OVERLAPPING"),

    PUT_OBJECT_TO_S3_FAILED("PUT_OBJECT_TO_S3_FAILED"),


    //============== DMS ================

    ALERT_NAME_WAS_EXISTED ("ALERT_NAME_WAS_EXISTED"),

    UNAUTHORIZED ("UNAUTHORIZED"),
    INTERNAL_SERVER_ERROR ("INTERNAL_SERVER_ERROR");

    private final String errorDetail;

    EarableErrorCode(String errorDetail) {
        this.errorDetail = errorDetail;
    }
}
