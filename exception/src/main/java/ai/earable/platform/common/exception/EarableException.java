package ai.earable.platform.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by BinhNH on 3/17/22
 */
@Getter
@Setter
public class EarableException extends RuntimeException{
    private int httpStatusCode;
    private String earableErrorCode;
    private String details;

    public EarableException(int httpStatusCode, String earableErrorCode, String details){
        super(details);
        this.httpStatusCode = httpStatusCode;
        this.earableErrorCode = earableErrorCode;
        this.details = details;
    }

    public EarableException(int httpStatusCode, String earableErrorCode){
        this(httpStatusCode, earableErrorCode, ""); //TODO: Handle this message
    }
}
