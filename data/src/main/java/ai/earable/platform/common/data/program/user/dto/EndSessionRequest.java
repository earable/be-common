package ai.earable.platform.common.data.program.user.dto;

import ai.earable.platform.common.data.ValidationUtils;
import ai.earable.platform.common.data.exception.EarableErrorCode;
import ai.earable.platform.common.data.exception.EarableException;
import ai.earable.platform.common.data.program.common.enums.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EndSessionRequest {
    private ActivityType activityType;
    private Long startTime;
    private Long endTime;
    private Integer duration;
    private Integer sessionPoint;
    private Integer dayOfYear;
    private Integer weekOfYear;
    private Integer monthOfYear;
    private Integer year;

    public void validate() {
        ValidationUtils.checkNull("ActivityType", this.activityType);
        ValidationUtils.checkNull("startTime", this.startTime);
        ValidationUtils.checkNull("sessionPoint", this.sessionPoint);
        ValidationUtils.checkNull("dayOfYear", this.dayOfYear);
        ValidationUtils.checkNull("weekOfYear", this.weekOfYear);
        ValidationUtils.checkNull("monthOfYear", this.monthOfYear);
        ValidationUtils.checkNull("year", this.year);

        if (null == this.endTime && null == this.duration)
            throw new EarableException(400, EarableErrorCode.PARAM_REQUIRED, "endTime or duration");
    }
}
