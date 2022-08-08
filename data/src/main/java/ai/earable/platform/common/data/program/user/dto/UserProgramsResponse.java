package ai.earable.platform.common.data.program.user.dto;

import ai.earable.platform.common.data.program.cms.dto.EarableBadgeResponse;
import ai.earable.platform.common.data.program.common.model.Program;
import ai.earable.platform.common.data.program.user.enums.UserProgramState;
import ai.earable.platform.common.data.program.user.enums.UserProgramStatus;
import ai.earable.platform.common.data.program.user.model.UserProgramBadgeInfo;
import ai.earable.platform.common.data.program.user.model.UserTask;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProgramsResponse extends Program {
    private UUID userId;
    private UUID programId;
    private Integer totalTasks;
    private Integer tasksCompleted;
    private UserProgramStatus userProgramStatus;
    private Integer participants;
    private Integer currentPoint;
    private List<UserTask> userTasks;
    private List<UserProgramBadgeInfo> userProgramBadgeInfos;
    private UserProgramState state;
}
