package ai.earable.platform.common.exception.exception;

import lombok.*;

/**
 * Created by BinhNH on 3/18/22
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDetails {
    private int httpStatusCode;
    private String earableErrorCode;
    private String details;
}
