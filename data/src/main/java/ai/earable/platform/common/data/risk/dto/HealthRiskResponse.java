package ai.earable.platform.common.data.risk.dto;

import ai.earable.platform.common.data.risk.enums.HealthRiskAlertTime;
import ai.earable.platform.common.data.risk.enums.HealthRiskType;
import ai.earable.platform.common.data.risk.enums.HealthRiskUnit;
import ai.earable.platform.common.data.risk.model.HealthRiskRule;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@With
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HealthRiskResponse {
    private UUID id;
    private HealthRiskType type;
    private String name;
    private String description;
    private String icon;
    private String image;
    private Boolean isActive;
    private HealthRiskUnit unit;
    private String ruleQuery;
    private String occurrenceQuery;
    private String crossingDirectionType;
    private HealthRiskAlertTime alertTime;
    private List<HealthRiskRule> healthRiskRules;
}
