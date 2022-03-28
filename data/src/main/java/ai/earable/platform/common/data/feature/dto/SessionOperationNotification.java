package ai.earable.platform.common.data.feature.dto;

import ai.earable.platform.common.data.feature.model.SessionEvent;
import lombok.*;

/**
 * Created by BinhNH on 3/28/2022
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionOperationNotification {
    private String featureName;
    private String profileId;
    private String deviceId;
    private String sessionId;
    private SessionEvent event;
}
