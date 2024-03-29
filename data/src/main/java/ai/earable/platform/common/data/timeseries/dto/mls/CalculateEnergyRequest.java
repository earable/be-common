package ai.earable.platform.common.data.timeseries.dto.mls;

import lombok.*;

import java.util.List;

/**
 * Created by BinhNH on 9/10/22
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalculateEnergyRequest {
    private double recovery_score;
    private List<List<Double>> focus_session_trends; //focus score
    private List<Integer> focus_session_relative_timestamps; //In hour
}
