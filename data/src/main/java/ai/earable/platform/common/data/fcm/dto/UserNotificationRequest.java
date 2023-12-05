package ai.earable.platform.common.data.fcm.dto;

import ai.earable.platform.common.data.ValidationUtils;
import ai.earable.platform.common.data.exception.EarableErrorCode;
import ai.earable.platform.common.data.exception.EarableException;
import ai.earable.platform.common.data.notification.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author DungNT
 * @date 05/12/2023
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserNotificationRequest {
    @NotBlank(message = "title is required")
    private String title;
    @NotBlank(message = "body is required")
    private String body;
    private String description;
    @NotBlank(message = "eventName is required")
    private String eventName;
    private String itemDetailId;
    private boolean pushNow;

    public void validate() {
        ValidationUtils.checkNull("eventName", this.eventName);

        if (!StringUtils.isEmpty(itemDetailId)) {
            try {
                UUID.fromString(itemDetailId);
            } catch (Exception exception) {
                throw new EarableException(400, EarableErrorCode.PARAM_INVALID, "itemDetailId");
            }
        }
    }
}
