package ai.earable.platform.common.data.feature.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private Map<String, String> metadata;

    /**
     * The clientTimestamp at the moment of session started in client timezone
     */
    private long clientTimestamp;

    /**
     * The client timezone at the moment of session started
     */
    private String timezone;


}
