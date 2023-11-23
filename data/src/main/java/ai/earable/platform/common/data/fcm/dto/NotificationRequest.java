package ai.earable.platform.common.data.fcm.dto;

import ai.earable.platform.common.data.notification.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author DungNT
 * @date 03/11/2023
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {
    @NotNull(message = "userId is required")
    private UUID userId;
    @NotNull(message = "type is required")
    private NotificationType type;
    @NotBlank(message = "title is required")
    private String title;
    @NotBlank(message = "body is required")
    private String body;
    private String description;
    @NotBlank(message = "itemDetailId is required")
    private String itemDetailId;
    private boolean pushNow;
}
