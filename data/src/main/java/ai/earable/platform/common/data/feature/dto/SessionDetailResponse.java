package ai.earable.platform.common.data.feature.dto;

import ai.earable.platform.common.data.feature.model.SessionMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionDetailResponse {
    private String userId;
    private String profileId;
    private String featureName;
    private long startedTime;
    private String sessionId;
    private SessionMode mode;
    private String sessionSettingId;
    private String deviceId;
    private long endedTime;
    private long clientTimestamp;
    private String timezone;
    private Map<String, String> metadata;
}
