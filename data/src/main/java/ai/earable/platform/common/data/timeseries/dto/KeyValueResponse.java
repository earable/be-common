package ai.earable.platform.common.data.timeseries.dto;

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
public class KeyValueResponse {
    private String status;
    private List<Map<String, String>> data;
}
