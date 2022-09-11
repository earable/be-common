package ai.earable.platform.common.data.timeseries.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by BinhNH on 9/10/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefaultReportInformation {
    private String author;
    private String description;
    private String version;
    private List<DefaultReportMetric> defaultReportMetricList;
}
