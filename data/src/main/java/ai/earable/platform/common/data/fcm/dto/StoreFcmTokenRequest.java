package ai.earable.platform.common.data.fcm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author Hungnv
 * @date 22/07/2022
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreFcmTokenRequest {
    @NotBlank(message = "deviceId is required")
    private String deviceId;
    @NotBlank(message = "fcmToken is required")
    private String fcmToken;
}
