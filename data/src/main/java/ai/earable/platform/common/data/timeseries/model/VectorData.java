package ai.earable.platform.common.data.timeseries.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by BinhNH on 3/17/22
 */
@Getter
@Setter
@NoArgsConstructor
public class VectorData {
    private String resultType;
    private List<VectorResult> result;
}
