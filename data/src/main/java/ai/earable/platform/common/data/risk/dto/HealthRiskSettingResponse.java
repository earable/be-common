package ai.earable.platform.common.data.risk.dto;

import ai.earable.platform.common.data.risk.enums.HealthRiskSettingStatus;
import ai.earable.platform.common.data.risk.enums.HealthRiskType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@With
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HealthRiskSettingResponse {
    private UUID id;
    private String name;
    private HealthRiskType type;
    private String description;
    private String icon;
    private String image;
    private HealthRiskSettingStatus status;
    private Integer notificationRemainingDays;
}
