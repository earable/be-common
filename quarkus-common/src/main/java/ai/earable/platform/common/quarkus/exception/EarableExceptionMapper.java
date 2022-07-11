package ai.earable.platform.common.quarkus.exception;

import ai.earable.platform.common.data.exception.EarableException;
import ai.earable.platform.common.data.exception.ErrorDetails;
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
        String errorDetail = e.getDetails() == null ?  "Something went wrong?" : e.getDetails();
        ErrorDetails errorDetails = ErrorDetails.builder()
            .httpStatusCode(e.getHttpStatusCode())
            .earableErrorCode(e.getEarableErrorCode() != null ? e.getEarableErrorCode().name() : e.getErrorCode())
            .details(errorDetail).build();
        log.error("Return error to client with details {}", errorDetail);
        return Response.status(errorDetails.getHttpStatusCode())
                .entity(errorDetails).build();
    }
}
