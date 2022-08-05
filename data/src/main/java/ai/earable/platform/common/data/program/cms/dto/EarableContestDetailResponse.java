package ai.earable.platform.common.data.program.cms.dto;

import ai.earable.platform.common.data.program.cms.enums.RepeatType;
import ai.earable.platform.common.data.program.cms.enums.ProgramStatus;
import ai.earable.platform.common.data.program.cms.model.BadgeProgram;
import ai.earable.platform.common.data.program.cms.model.EarableReward;
import ai.earable.platform.common.data.program.cms.model.EarableTask;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@With
public class EarableContestDetailResponse {
    private UUID id;
    private String name;
    private RepeatType type;
    private Long startDate;
    private Long endDate;
    private String icon;
    private String information;
    private String shortDescription;
    private Integer requiredPoint;
    private ProgramStatus status;
    private Integer participant;
    private List<BadgeProgram> earableBadges;
    private List<EarableReward> earableRewards;
    private List<EarableTask> earableTasks;

}
