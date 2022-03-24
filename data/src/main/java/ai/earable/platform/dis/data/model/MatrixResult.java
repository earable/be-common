package ai.earable.platform.dis.data.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Created by BinhNH on 3/17/22
 */
@Getter
@NoArgsConstructor
public class MatrixResult {
    /**
     * The information about:
     * metric name
     * other tags
     */
    private Map<String, String> metric;

    /**
     * Series of values of this metric
     * index 0: timestamp
     * index 1: value
     */
    private List<List<String>> values;
}
