package ai.earable.platform.common.data.timeseries.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by BinhNH on 3/17/22
 */
@Getter
@NoArgsConstructor
public class MatrixData {
    /**
     * The type of result (“matrix” or “vector”)
     */
    private String resultType;

    /**
     * List of result follow time-series format returned from VictoriaMetricDB
     */
    private List<MatrixResult> result;
}
