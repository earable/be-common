package ai.earable.platform.common.webflux.exception;

import ai.earable.platform.common.data.exception.EarableErrorCode;
import ai.earable.platform.common.data.exception.EarableException;
import ai.earable.platform.common.data.exception.ErrorDetails;
import ai.earable.platform.common.webflux.utils.MessageUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ErrorWebExceptionHandlerImpl implements ErrorWebExceptionHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    MessageUtils messageUtils;

    private <T> Mono<Void> write(ServerHttpResponse httpResponse, T object) {
        return httpResponse
                .writeWith(Mono.fromSupplier(() -> {
                    DataBufferFactory bufferFactory = httpResponse.bufferFactory();
                    try {
                        return bufferFactory.wrap(objectMapper.writeValueAsBytes(object));
                    } catch (Exception ex) {
                        log.warn("Error writing response", ex);
                        return bufferFactory.wrap(new byte[0]);
                    }
                }));
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }

        if (ex instanceof EarableException) {
            EarableException e = (EarableException) ex;

            exchange.getResponse().setStatusCode(HttpStatus.valueOf(e.getHttpStatusCode()));

            String detail = e.getEarableErrorCode() != null ? messageUtils.getMessage(e.getEarableErrorCode().name(), e.getParam())
                    : e.getDetails();

            ErrorDetails errorDetails = ErrorDetails.builder()
                    .httpStatusCode(e.getHttpStatusCode())
                    .earableErrorCode(e.getEarableErrorCode() != null ? e.getEarableErrorCode().name() : e.getErrorCode())
                    .details(detail).build();
            return this.write(exchange.getResponse(), errorDetails);
        }

        log.error(ex.getLocalizedMessage());
        exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return this.write(exchange.getResponse(), EarableErrorCode.INTERNAL_SERVER_ERROR.toString());
    }
}