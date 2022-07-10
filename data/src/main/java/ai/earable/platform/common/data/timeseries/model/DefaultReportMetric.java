package ai.earable.platform.common.data.timeseries.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by BinhNH on 7/10/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefaultReportMetric implements Serializable {
    /**
     * The new metric will be reported
     */
    private String metricName;

    /**
     * The template of victoria metric query
     * Backend will use this template to replace the necessary filter to query to victoria by DES
     */
    private String queryTemplate;

    @JsonIgnore
    private double value;
}
