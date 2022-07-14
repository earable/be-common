package ai.earable.platform.common.data.timeseries.model;

import lombok.*;

/**
 * Created by BinhNH on 3/17/22
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonitoredDataValue {
    /**
     * The double value of this data metric
     */
    private String value;

    /**
     * The timestamp of above value.
     * If not present in the client request, this will be filled by timestamp at BE site.
     */
    private long timestamp;
}
