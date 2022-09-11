package ai.earable.platform.common.data.timeseries.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by BinhNH on 7/10/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefaultReportMetric {
    /**
     * The new metric will be reported
     */
    private String metricName;

    /**
     * Describe details about this metric
     */
    private String description;

    /**
     * The template of victoria metric query
     * Backend will use this template to replace the necessary filter to query to victoria by DES
     */
    private String queryTemplate;

    /**
     * If this value is true, this query is not supported by timeseries victoria
     * So we need implementation
     */
    private boolean needSelfImpl = false;
}
