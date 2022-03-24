package ai.earable.platform.common.data.timeseries.model;

import lombok.*;

import java.util.List;
import java.util.Map;

/**
 * Created by BinhNH on 3/17/22
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonitoredData {
    /**
     * The metric name created by client
     */
    private String metricName;

    /**
     * Map of tag name and tag value defined by user
     */
    private Map<String, String> tags;

    /**
     * Series of values of this metric
     */
    private List<MonitoredDataValue> monitoredDataValues;

}
