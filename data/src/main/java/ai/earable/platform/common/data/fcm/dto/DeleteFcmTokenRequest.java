package ai.earable.platform.common.data.fcm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author Hungnv
 * @date 22/07/2022
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteFcmTokenRequest {
    @NotBlank(message = "deviceId is required")
    private String deviceId;
    @NotNull(message = "userId is required")
    private UUID userId;
}
