package ai.earable.platform.common.data.program.dto;

import ai.earable.platform.common.data.program.enums.ProgramRepeatType;
import ai.earable.platform.common.data.program.enums.ProgramStatus;
import ai.earable.platform.common.data.program.model.BadgeProgram;
import ai.earable.platform.common.data.program.model.EarableReward;
import ai.earable.platform.common.data.program.model.EarableTask;
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

}
