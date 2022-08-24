package ai.earable.platform.common.data.program.cms.dto;

import ai.earable.platform.common.data.program.cms.enums.EarableProgramStatus;
import ai.earable.platform.common.data.program.cms.model.EarableProgramBadgeInfo;
import ai.earable.platform.common.data.program.cms.model.EarableTask;
import ai.earable.platform.common.data.program.common.model.Program;
import ai.earable.platform.common.data.program.common.model.Reward;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@With
public class EarableProgramDetailResponse extends Program {
    private UUID programId;
    private List<EarableProgramBadgeInfo> earableProgramBadgeInfos;
    private List<Reward> rewards;
    private List<EarableTask> earableTasks;
    private EarableProgramStatus status;
    private Integer participant;
    private Boolean canJoin;
}
