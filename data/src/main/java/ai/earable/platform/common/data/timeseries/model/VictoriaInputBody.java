package ai.earable.platform.common.data.timeseries.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BinhNH on 3/17/22
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VictoriaInputBody {
    private List<String> dataLines;

    public void add(String dataPoint){
        if(dataLines == null) dataLines = new ArrayList<>();
        dataLines.add(dataPoint);
    }
}
