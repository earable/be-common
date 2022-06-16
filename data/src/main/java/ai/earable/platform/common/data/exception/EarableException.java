package ai.earable.platform.common.data.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by BinhNH on 3/17/22
 */
@Getter
@Setter
public class EarableException extends RuntimeException{
    private int httpStatusCode;
    private EarableErrorCode earableErrorCode;
    private String errorCode;
    private String details;
    private String param;

    public EarableException(int httpStatusCode, EarableErrorCode commonErrorCode, String param){
        super(param);
        this.httpStatusCode = httpStatusCode;
        this.earableErrorCode = commonErrorCode;
        this.param = param;
    }

    public EarableException(int httpStatusCode, String errorCode, String details) {
        this.httpStatusCode = httpStatusCode;
        this.errorCode = errorCode;
        this.details = details;
    }

    public EarableException(int httpStatusCode, EarableErrorCode earableErrorCode){
        this(httpStatusCode, earableErrorCode, "");
    }
}
