package ai.earable.platform.common.data.timeseries.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * Created by BinhNH on 3/17/22
 */
@Getter
@Setter
@NoArgsConstructor
public class VectorResult {
    private Map<String, String> metric;
    private List<String> value;
}
