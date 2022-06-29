package ai.earable.platform.common.data.feature.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FcmToken {
    private String userId;
    private String deviceId;
    private String fcmToken;
    private long updatedTime;
}
