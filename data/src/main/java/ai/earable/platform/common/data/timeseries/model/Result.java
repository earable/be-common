package ai.earable.platform.common.data.timeseries.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Created by BinhNH on 3/17/22
 */
@Getter
@NoArgsConstructor
public class Result {
    /**
     * The information about:
     * metric name
     * other tags
     */
    private Map<String, String> metric;

    /**
     * Series of values of this metric
     * Using in case of matrix data
     * index 0: timestamp
     * index 1: value
     */
    private List<List<String>> values;

    /**
     * Value of this metric
     * Using in case of vector data
     * index 0: timestamp
     * index 1: value
     */
    private List<String> value;
}
