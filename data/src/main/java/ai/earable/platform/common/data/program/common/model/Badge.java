package ai.earable.platform.common.data.program.common.model;

import ai.earable.platform.common.data.program.common.enums.BenefitType;
import ai.earable.platform.common.data.program.common.enums.SessionPoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
}
