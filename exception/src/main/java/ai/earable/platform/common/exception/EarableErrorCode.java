package ai.earable.platform.common.exception;

import lombok.Getter;

/**
 * Created by BinhNH on 3/19/22
 */
@Getter
public enum EarableErrorCode {
    SIZE_LIMIT_INVALID ("SIZE_LIMIT_INVALID"),
    PARAM_PAGING_INVALID ("PARAM_PAGING_INVALID"),

    INTERNAL_SERVER_ERROR ("INTERNAL_SERVER_ERROR");

    private final String errorDetail;

    EarableErrorCode(String errorDetail) {
        this.errorDetail = errorDetail;
    }
}
