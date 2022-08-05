package ai.earable.platform.common.data.program.cms.dto;

import ai.earable.platform.common.data.program.enums.BenefitType;
import ai.earable.platform.common.data.program.enums.SessionPoint;
import lombok.*;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Data
@Builder
@UserDefinedType
@NoArgsConstructor
@AllArgsConstructor
@With
public class EarableBadgeCreateResponse {
    private String badgeId;
    private String title;
    private String description;
    private String icon;
    private Double benefitValue;
    private BenefitType benefitType;
    private SessionPoint sessionPoint;
    private Integer benefitEffectivePeriod;
}
