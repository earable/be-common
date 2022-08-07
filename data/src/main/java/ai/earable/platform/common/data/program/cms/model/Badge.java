package ai.earable.platform.common.data.program.cms.model;

import ai.earable.platform.common.data.program.cms.enums.BenefitType;
import ai.earable.platform.common.data.program.cms.enums.Receiver;
import ai.earable.platform.common.data.program.cms.enums.SessionPoint;
import ai.earable.platform.common.data.program.user.enums.UserBadgeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.io.Serializable;


@Deprecated
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Badge implements Serializable {
    protected String badgeId;
    protected String title;
    protected String description;
    protected String icon;
    protected Double benefitValue;
    protected BenefitType benefitType;
    protected SessionPoint sessionPoint;
    protected Integer benefitEffectivePeriod;
    protected Long createAt;
    private UserBadgeStatus status;
}
