package ai.earable.platform.common.data.program.cms.model;

import ai.earable.platform.common.data.program.enums.BenefitType;
import ai.earable.platform.common.data.program.enums.Receiver;
import ai.earable.platform.common.data.program.enums.SessionPoint;
import lombok.*;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Deprecated
@Data
@Builder
@UserDefinedType
@NoArgsConstructor
@AllArgsConstructor
@With
public class BadgeProgram {
    private String badgeId;
    private String title;
    private String description;
    private String icon;
    private Double benefitValue;
    private BenefitType benefitType;
    private SessionPoint sessionPoint;
    private Integer benefitEffectivePeriod;
    private Receiver receiver;
}
