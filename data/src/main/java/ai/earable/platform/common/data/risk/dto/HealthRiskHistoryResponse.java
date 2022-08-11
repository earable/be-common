package ai.earable.platform.common.data.risk.dto;

import ai.earable.platform.common.data.risk.enums.HealthRiskSeverity;
import ai.earable.platform.common.data.risk.enums.HealthRiskType;
import ai.earable.platform.common.data.risk.model.HealthRiskHistory;
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
public class HealthRiskHistoryResponse implements Comparable<HealthRiskHistoryResponse> {
    private UUID id;
    private String name;
    private HealthRiskType healthRiskType;
    private String description;
    private String icon;
    private String image;
    private List<HealthRiskRule> healthRiskRules;
    private HealthRiskRule healthRiskRule;
    private List<HealthRiskHistory> healthRiskHistories;

    @Override
    public int compareTo(HealthRiskHistoryResponse o) {
        if (this.getHealthRiskRule().getStatus().equals(HealthRiskSeverity.NORMAL) && o.getHealthRiskRule().getStatus().equals(HealthRiskSeverity.WARNING)) {
            return -1;
        }

        if (this.getHealthRiskRule().getStatus().equals(HealthRiskSeverity.WARNING) && o.getHealthRiskRule().getStatus().equals(HealthRiskSeverity.EMERGENCY)) {
            return -1;
        }

        if (this.getHealthRiskRule().getStatus().equals(HealthRiskSeverity.NORMAL) && o.getHealthRiskRule().getStatus().equals(HealthRiskSeverity.EMERGENCY)) {
            return -1;
        }

        if (this.getHealthRiskRule().getStatus().equals(HealthRiskSeverity.WARNING) && o.getHealthRiskRule().getStatus().equals(HealthRiskSeverity.NORMAL)) {
            return 1;
        }

        if (this.getHealthRiskRule().getStatus().equals(HealthRiskSeverity.EMERGENCY) && o.getHealthRiskRule().getStatus().equals(HealthRiskSeverity.WARNING)) {
            return 1;
        }

        if (this.getHealthRiskRule().getStatus().equals(HealthRiskSeverity.EMERGENCY) && o.getHealthRiskRule().getStatus().equals(HealthRiskSeverity.NORMAL)) {
            return 1;
        }
        return this.getName().compareTo(o.getName()) * -1;
    }
}
