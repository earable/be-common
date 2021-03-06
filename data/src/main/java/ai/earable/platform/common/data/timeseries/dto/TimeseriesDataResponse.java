package ai.earable.platform.common.data.timeseries.dto;

import ai.earable.platform.common.data.timeseries.model.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by BinhNH on 3/17/22
 */
@Getter
@Setter
@NoArgsConstructor
public class TimeseriesDataResponse {
    private String status;
    private Data data;
}
