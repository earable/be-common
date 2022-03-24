package ai.earable.platform.dis.data.dto;

import ai.earable.platform.dis.data.model.MonitoredData;
import lombok.*;

import java.util.List;

/**
 * Created by BinhNH on 3/17/22
 * This object will be used in case client send writing data request to BE.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataWritingRequest {
    private String featureName;
    private String profileId;
    private String deviceId;
    private String sessionId;

    /**
     * List of metrics, tags and data value.
     */
    private List<MonitoredData> monitoredDataList;
}
