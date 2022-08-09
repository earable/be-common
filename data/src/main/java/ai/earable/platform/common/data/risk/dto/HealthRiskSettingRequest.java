package ai.earable.platform.common.data.risk.dto;

import ai.earable.platform.common.data.risk.enums.HealthRiskSettingStatus;
import ai.earable.platform.common.data.risk.enums.HealthRiskType;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HealthRiskSettingRequest {
    @NotNull(message = "HealthRiskType is required!")
    private HealthRiskType healthRiskType;
    @NotNull(message = "Status is required!")
    private HealthRiskSettingStatus healthRiskSettingStatus;
}
