package ai.earable.platform.common.quarkus.exception;

import ai.earable.platform.common.data.exception.EarableErrorCode;
import ai.earable.platform.common.data.exception.ErrorDetails;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;


/**
 * Created by BinhNH on 3/18/22
 */
@Provider
@Slf4j
public class ExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<Exception>  {
    @Override
    public Response toResponse(Exception e) {
        ErrorDetails errorDetails = ErrorDetails.builder()
            .httpStatusCode(500)
            .earableErrorCode(EarableErrorCode.INTERNAL_SERVER_ERROR.getErrorDetail())
            .details(e.getLocalizedMessage()).build();
        log.error("Return error to client with details {}", e.getLocalizedMessage());
        return Response.status(errorDetails.getHttpStatusCode())
                .entity(errorDetails).build();
    }
}