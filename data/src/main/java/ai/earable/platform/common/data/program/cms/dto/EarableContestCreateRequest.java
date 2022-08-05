package ai.earable.platform.common.data.program.cms.dto;

import ai.earable.platform.common.data.ValidationUtils;
import ai.earable.platform.common.data.program.cms.enums.ProgramRepeatType;
import ai.earable.platform.common.data.program.cms.enums.ProgramStatus;
import ai.earable.platform.common.data.program.cms.model.BadgeProgram;
import ai.earable.platform.common.data.program.cms.model.EarableReward;
import ai.earable.platform.common.data.program.cms.model.EarableTask;
import lombok.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@With
public class EarableContestCreateRequest {
    private UUID id;
    private String name;
    private ProgramRepeatType type;
    private Long startDate;
    private Long endDate;
    private String icon;
    private String information;
    private String shortDescription;
    private Integer requiredPoint;
    private ProgramStatus status;

    private List<BadgeProgram> earableBadges;
    private List<EarableReward> earableRewards;
    private List<EarableTask> earableTasks;

    public void validate(){
        if (this.earableTasks.stream().map(o->o.getId())==null) {
            earableTasks.stream().map(o -> o.setId(UUID.randomUUID())).collect(Collectors.toList());
        }
        ValidationUtils.checkBlank("name",this.name);
        ValidationUtils.checkNull("type",this.type);
        ValidationUtils.checkNull("startDate",this.startDate);
        ValidationUtils.checkNull("endDate",this.endDate);
        ValidationUtils.checkBlank("icon",this.icon);
        ValidationUtils.checkBlank("information",this.information);
        ValidationUtils.checkBlank("shortDescription",this.shortDescription);
        ValidationUtils.checkNull("earableRewards",this.earableRewards);
    }
}
