package ai.earable.platform.common.data.program.cms.dto;

import ai.earable.platform.common.data.ValidationUtils;
import ai.earable.platform.common.data.exception.EarableErrorCode;
import ai.earable.platform.common.data.exception.EarableException;
import ai.earable.platform.common.data.program.common.enums.ProgramType;
import ai.earable.platform.common.data.program.common.enums.RepeatType;
import ai.earable.platform.common.data.program.cms.model.EarableProgramBadgeInfo;
import ai.earable.platform.common.data.program.common.model.Reward;
import ai.earable.platform.common.data.program.cms.model.EarableTask;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EarableProgramCreateRequest {
    private String name;
    private ProgramType programType;
    private RepeatType type;
    private String icon;
    private Integer duration;
    private String information;
    private String shortDescription;
    private Integer requiredPoint;
    private List<EarableProgramBadgeInfo> earableProgramBadgeInfos;
    private List<Reward> rewards;
    private List<EarableTask> earableTasks;

    /**
     * Used for CONTEST only
     * TODO: LongNH validate this
     */
    private Long startDate;

    /**
     * Used for CONTEST only
     * TODO: LongNH validate this
     */
    private Long endDate;


    public void validate(){
        ValidationUtils.checkBlank("name",this.name);
        ValidationUtils.checkBlank("icon",this.icon);
        ValidationUtils.checkBlank("information",this.information);
        ValidationUtils.checkBlank("shortDescription",this.shortDescription);
        ValidationUtils.checkNull("requiredPoint",this.requiredPoint);
        ValidationUtils.checkNull("earableRewards",this.rewards);
//        ValidationUtils.checkNull("earableTasks",this.earableTasks);

        for (EarableTask earableTask : earableTasks){
            if (earableTask.getTaskId() == null)
                earableTask.setTaskId(UUID.randomUUID());
        }

        for (Reward reward : rewards){
            if (reward.getDescription().length() > 60)
                throw new EarableException(400,EarableErrorCode.PARAM_INVALID,"description max 60 characters");
            if (reward.getTitle().length() > 60)
                throw new EarableException(400,EarableErrorCode.PARAM_INVALID,"title max 60 characters");
        }

        if (programType.equals(ProgramType.CONTEST)){
            if (this.startDate == null || this.endDate == null)
                throw new EarableException(400,EarableErrorCode.PARAM_INVALID,"startDate = null or endDate = null");
            if (this.duration != null)
                throw new EarableException(400,EarableErrorCode.PARAM_INVALID,"duration");

        }else if (programType.equals(ProgramType.CHALLENGE)){
            if (this.startDate !=null || this.endDate!=null)
                throw new EarableException(400,EarableErrorCode.PARAM_INVALID,"startDate != null or endDate != null");
        }

        if (null != this.duration && this.duration <0)
            throw new EarableException(400, EarableErrorCode.PARAM_INVALID,"duration");

        if (this.requiredPoint <0)
            throw new EarableException(400, EarableErrorCode.PARAM_INVALID,"requiredPoint");

        if (type.equals(RepeatType.DAILY)){
            if (null != this.duration && this.duration>1)
                throw new EarableException(400,EarableErrorCode.PARAM_INVALID,"duration");

        }else if(type.equals(RepeatType.WEEKLY)){
            if (null != this.duration && this.duration>7)
                throw new EarableException(400,EarableErrorCode.PARAM_INVALID,"duration");

        } else if (type.equals(RepeatType.MONTHLY)) {
            if (null != this.duration && this.duration>30)
                throw new EarableException(400, EarableErrorCode.PARAM_INVALID,"duration");

        } else if (type.equals(RepeatType.QUARTERLY)) {
            if (null != this.duration && this.duration>90)
                throw new EarableException(400, EarableErrorCode.PARAM_INVALID,"duration");

        } else if (type.equals(RepeatType.YEARLY)) {
            if (null != this.duration && this.duration>365)
                throw new EarableException(400,EarableErrorCode.PARAM_INVALID,"duration");
        }
    }
}
