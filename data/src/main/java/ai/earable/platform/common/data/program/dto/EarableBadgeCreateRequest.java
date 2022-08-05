package ai.earable.platform.common.data.program.dto;

import ai.earable.platform.common.data.ValidationUtils;
import ai.earable.platform.common.data.exception.EarableErrorCode;
import ai.earable.platform.common.data.exception.EarableException;
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
