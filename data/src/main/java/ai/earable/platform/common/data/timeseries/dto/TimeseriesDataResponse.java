package ai.earable.platform.common.data.timeseries.dto;

import ai.earable.platform.common.data.timeseries.model.Data;
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
public class TimeseriesDataResponse {
    private String status;
    private Data data;

    public Map<String, String> getTags(){
        return data.getResult().get(0).getMetric();
    }

    public List<String> getVectorValue(){
        return data.getResult().get(0).getValue();
    }

    public List<List<String>> getMatrixValues(){
        return data.getResult().get(0).getValues();
    }
}
