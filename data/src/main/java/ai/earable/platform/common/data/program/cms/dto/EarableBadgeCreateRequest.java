package ai.earable.platform.common.data.program.cms.dto;

import ai.earable.platform.common.data.ValidationUtils;
import ai.earable.platform.common.data.exception.EarableErrorCode;
import ai.earable.platform.common.data.exception.EarableException;
import ai.earable.platform.common.data.program.common.enums.BenefitType;
import ai.earable.platform.common.data.program.common.enums.SessionPoint;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@With
public class EarableBadgeCreateRequest {
    private String title;
    private String description;
    private String icon;
    private Double benefitValue;
    private BenefitType benefitType;
    private SessionPoint sessionPoint;
    private Integer benefitEffectivePeriod;

    public void validate(){
        ValidationUtils.checkBlank("title",this.title);
        ValidationUtils.checkBlank("description",this.description);
        ValidationUtils.checkBlank("icon",this.icon);
        ValidationUtils.checkNull("benefitValue",this.benefitValue);
        ValidationUtils.checkNull("benefitType",this.benefitType);

        if (this.title.length() > 30)
            throw new EarableException(400,EarableErrorCode.PARAM_INVALID,"title max character 30");

        if (this.description.length() > 60)
            throw new EarableException(400, EarableErrorCode.PARAM_INVALID,"description max character 60");

        if (benefitType.equals(BenefitType.POINT)){
            if (null!= this.benefitEffectivePeriod)
                throw new EarableException(400, EarableErrorCode.PARAM_INVALID,"benefiEffectivePeriod");
            if (null!= this.sessionPoint)
                throw new EarableException(400, EarableErrorCode.PARAM_INVALID,"sessionPoint");
        }
        if (benefitType.equals(BenefitType.PERCENTAGE)){
            if (null == this.benefitEffectivePeriod)
                throw new EarableException(400,EarableErrorCode.PARAM_INVALID,"benefitEffectivePeriod");
            if (null == this.sessionPoint)
                throw new EarableException(400, EarableErrorCode.PARAM_INVALID,"sessionPoint");
        }
    }
}