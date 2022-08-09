package ai.earable.platform.common.data.program.cms.model;

import ai.earable.platform.common.data.program.common.enums.BenefitType;
import ai.earable.platform.common.data.program.common.enums.Receiver;
import ai.earable.platform.common.data.program.common.enums.SessionPoint;
import ai.earable.platform.common.data.program.common.model.Badge;
import lombok.*;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.UUID;

@Data
@Builder
@UserDefinedType
@NoArgsConstructor
@AllArgsConstructor
@With
public class EarableProgramBadgeInfo {
    private UUID badgeId;
    private Receiver receiver;
    protected String title;
    protected String description;
    protected String icon;
    @Column("benefit_value")
    protected Double benefitValue;
    @Column("benefit_type")
    protected BenefitType benefitType;
    @Column("session_point")
    protected SessionPoint sessionPoint;
    @Column("benefit_effective_period")
    protected Integer benefitEffectivePeriod;
    protected Long createAt;
}
