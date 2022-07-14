package ai.earable.platform.common.data.timeseries.dto;

import ai.earable.platform.common.data.timeseries.model.MatrixData;
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
public class MatrixDataResponse {
    private String status;
    private MatrixData data;

    public Map<String, String> getTags(){
        return data.getResult().get(0).getMetric();
    }

    public List<List<String>> getValue(){
        return data.getResult().get(0).getValue();
    }
}
