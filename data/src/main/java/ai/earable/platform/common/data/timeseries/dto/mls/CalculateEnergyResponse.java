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
public class CalculateEnergyResponse {
    private List<String> user_brain_energy;
    private List<String> hours_since_wake;
}
