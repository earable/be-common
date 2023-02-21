package ai.earable.platform.common.webflux.exception;

import ai.earable.platform.common.data.exception.EarableErrorCode;
import ai.earable.platform.common.data.exception.EarableException;
import ai.earable.platform.common.data.exception.ErrorDetails;
import ai.earable.platform.common.webflux.utils.MessageUtils;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.MethodParameter;
import org.springframework.core.codec.DecodingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static ai.earable.platform.common.data.exception.EarableErrorCode.INPUT_INVALID;

/**
 * Created by BinhNH on 3/18/22
 */
@RestControllerAdvice
@Slf4j
public class EarableExceptionMapper {

    @Autowired
    MessageUtils messageUtils;

    @ExceptionHandler({ServerWebInputException.class})
    public ResponseEntity handleServerWebInputException(ServerWebInputException error) {
        log.error("handleServerWebInputException error stack trace", error);
        List<String> errMessages = new ArrayList<>();
        // handle for invalid request body
        if (error instanceof WebExchangeBindException && !((WebExchangeBindException) error).getAllErrors().isEmpty()) {
            errMessages = ((WebExchangeBindException) error).getAllErrors()
                .stream()
                .map(e -> {
                    var re = "\\{(.*?)\\}";
                    Pattern p = Pattern.compile(re);
                    Matcher m = p.matcher(e.getDefaultMessage());
                    String msg = "";
                    if (m.find()) {
                        msg = messageUtils.getMessage(m.group(1));
                    }

                    return StringUtils.isBlank(msg) ? e.getDefaultMessage() : msg;
                })
                .collect(Collectors.toList());
            ErrorDetails errorDetails = ErrorDetails.builder().httpStatusCode(HttpStatus.BAD_REQUEST.value())
                    .earableErrorCode(INPUT_INVALID.name()).details(errMessages.toString()).build();
            log.error("Return error to client with details {}", errorDetails.getDetails());
            return ResponseEntity.status(errorDetails.getHttpStatusCode()).body(errorDetails);
        }

        if(ObjectUtils.isNotEmpty(error.getCause()) && error.getCause() instanceof DecodingException) {
            DecodingException decodingException = (DecodingException) error.getCause();
            if(ObjectUtils.isNotEmpty(decodingException.getCause()) && decodingException.getCause() instanceof InvalidFormatException) {
                InvalidFormatException invalidFormatException = (InvalidFormatException) decodingException.getCause();
                List<JsonMappingException.Reference> references = invalidFormatException.getPath();
                errMessages = references.stream()
                        .map(reference -> String.format("The %s is invalid.", reference.getFieldName()))
                        .collect(Collectors.toList());
                ErrorDetails errorDetails = ErrorDetails.builder().httpStatusCode(HttpStatus.BAD_REQUEST.value())
                        .earableErrorCode(INPUT_INVALID.name()).details(errMessages.toString()).build();
                log.error("Return error to client with details {}", errorDetails.getDetails());
                return ResponseEntity.status(errorDetails.getHttpStatusCode()).body(errorDetails);
            }
        }
        // handle for ServerWebInputException
        MethodParameter parameter = error.getMethodParameter();
        String parameterName = ObjectUtils.isNotEmpty(parameter) ? parameter.getParameterName() : "";
        String reason = error.getReason();
        errMessages.add(String.format("%s %s", parameterName, reason));
        ErrorDetails errorDetails = ErrorDetails.builder().httpStatusCode(HttpStatus.BAD_REQUEST.value())
                .earableErrorCode(INPUT_INVALID.name()).details(errMessages.toString()).build();
        log.error("Return error to client with details {}", errorDetails.getDetails());
        return ResponseEntity.status(errorDetails.getHttpStatusCode()).body(errorDetails);
    }

    @ExceptionHandler(EarableException.class)
    public ResponseEntity handleEarableException(EarableException e) {
        log.error("handleEarableException error stack trace", e);
        String errorCode = e.getEarableErrorCode() != null ? e.getEarableErrorCode().name() : e.getErrorCode();
        String detail = !StringUtils.isBlank(errorCode) ? messageUtils.getMessageWithDefault(errorCode, e.getDetails(), e.getParam()) : e.getDetails();
        ErrorDetails errorDetails = ErrorDetails.builder()
            .httpStatusCode(e.getHttpStatusCode())
            .earableErrorCode( e.getEarableErrorCode() != null ? e.getEarableErrorCode().name() : e.getErrorCode())
            .details(detail).build();
        log.error("Return error to client with details {}", errorDetails.toString());
        return ResponseEntity.status(errorDetails.getHttpStatusCode())
                .body(errorDetails);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleCommonException(Exception e) {
        log.error("handleCommonException error stack trace", e);
        ErrorDetails errorDetails = ErrorDetails.builder()
            .httpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .earableErrorCode(EarableErrorCode.INTERNAL_SERVER_ERROR.name())
            .details(EarableErrorCode.INTERNAL_SERVER_ERROR.getErrorDetail()).build();
        log.error("Return error to client with details {}", errorDetails.toString());
        return ResponseEntity.status(errorDetails.getHttpStatusCode())
                .body(errorDetails);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public @ResponseBody ResponseEntity handleCommonException(AccessDeniedException e) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .httpStatusCode(HttpStatus.FORBIDDEN.value())
                .earableErrorCode(EarableErrorCode.ACCESS_DENIED.name())
                .details(EarableErrorCode.ACCESS_DENIED.getErrorDetail()).build();
        log.warn("Don't have permission access to resource {}", errorDetails.toString());
        return ResponseEntity.status(errorDetails.getHttpStatusCode())
                .body(errorDetails);
    }
}
