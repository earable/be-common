package ai.earable.platform.common.webflux.exception;

import ai.earable.platform.common.data.exception.EarableErrorCode;
import ai.earable.platform.common.data.exception.EarableException;
import ai.earable.platform.common.data.exception.ErrorDetails;
import ai.earable.platform.common.webflux.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by BinhNH on 3/18/22
 */
@RestControllerAdvice
@Slf4j
public class EarableExceptionMapper {

    @Autowired
    MessageUtils messageUtils;

    @ExceptionHandler(EarableException.class)
    public ResponseEntity handleEarableException(EarableException e) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .httpStatusCode(e.getHttpStatusCode())
                .earableErrorCode(e.getEarableErrorCode().name())
                .details(messageUtils.getMessage(e.getEarableErrorCode().name(),e.getParam())).build();
        log.error("Return error to client with details {}", errorDetails.toString());
        return ResponseEntity.status(errorDetails.getHttpStatusCode())
                .body(errorDetails);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleCommonException(Exception e) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .httpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .earableErrorCode(EarableErrorCode.INTERNAL_SERVER_ERROR.name())
                .details(EarableErrorCode.INTERNAL_SERVER_ERROR.getErrorDetail()).build();
        log.error("Return error to client with details {}", errorDetails.toString());
        return ResponseEntity.status(errorDetails.getHttpStatusCode())
                .body(errorDetails);
    }
}
