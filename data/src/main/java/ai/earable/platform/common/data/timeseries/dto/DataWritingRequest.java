package ai.earable.platform.common.data.timeseries.dto;

import ai.earable.platform.common.data.timeseries.model.MonitoredData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataWritingRequest implements Serializable, Cloneable {
    private String featureName;
    private String profileId;
    private String deviceId;
    private String sessionId;
    private String year;
    private String monthOfYear;
    private String weekOfYear;
    private String dayOfYear;

    /**
     * List of metrics, tags and data value.
     */
    private List<MonitoredData> monitoredDataList;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
