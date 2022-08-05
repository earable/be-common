package ai.earable.platform.common.data.program.dto;

import ai.earable.platform.common.data.program.enums.BenefitType;
import ai.earable.platform.common.data.program.enums.SessionPoint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EarableBadgeResponse {
    private String badgeId;
    private String title;
    private String description;
    private String icon;
    private Double benefitValue;
    private BenefitType benefitType;
    private Integer benefitEffectivePeriod;
    private SessionPoint sessionPoint;
    private Long createAt;
}
