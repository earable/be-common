package ai.earable.platform.common.data.program.cms.dto.filter;

import ai.earable.platform.common.data.program.common.enums.BenefitType;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@With
public class EarableBadgeFilterDto {
    private  String badgeId;
    private String title;
    private String description;
    private String icon;
    private Double benefitValue;
    private BenefitType benefitType;
    private Long createAt;
}
