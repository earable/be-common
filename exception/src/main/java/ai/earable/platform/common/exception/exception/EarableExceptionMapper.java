package ai.earable.platform.common.exception.exception;

import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * Created by BinhNH on 3/18/22
 */
@Provider
@Slf4j
public class EarableExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<EarableException> {
    @Override
    public Response toResponse(EarableException e) {
        ErrorDetails errorDetails = ErrorDetails.builder()
            .httpStatusCode(e.getHttpStatusCode())
            .earableErrorCode(e.getEarableErrorCode().name())
            .details(e.getDetails()).build();
        log.error("Return error to client with details {}", errorDetails.toString());
        return Response.status(errorDetails.getHttpStatusCode())
                .entity(errorDetails).build();
    }
}
