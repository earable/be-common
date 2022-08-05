package ai.earable.platform.common.data.program.user.model;

import ai.earable.platform.common.data.program.cms.enums.BenefitType;
import ai.earable.platform.common.data.program.user.enums.UserBadgeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.io.Serializable;


@Deprecated
@Data
@Builder
@UserDefinedType
@NoArgsConstructor
@AllArgsConstructor
public class Badge implements Serializable {
    private String badgeId;
    private String title;
    private String description;
    private String icon;
    private BenefitType benefitType;
    private Double benefitValue;
    private String sessionPoint;
    private Integer benefitEffectivePeriod;
    private UserBadgeStatus status;
}
