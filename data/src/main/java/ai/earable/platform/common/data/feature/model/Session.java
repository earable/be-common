package ai.earable.platform.common.data.feature.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Session implements Serializable {
    private String userId;
    private String profileId;
    private String featureName;
    private long startedTime;
    private long endedTime;
    private String sessionId;
    private String deviceId;
    private SessionMode mode;
    private String sessionSettingId;
    private long clientTimestamp;
    private String timezone;
    private Map<String, String> metadata;
}
