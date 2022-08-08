package ai.earable.platform.common.data.program.user.dto;

import ai.earable.platform.common.data.program.user.enums.UserProgramStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProgramsResponse {
    private UUID userId;
    private UUID programId;
    private String name;
    private Integer totalTasks;
    private Integer tasksCompleted;
    private float processGained;
    private Integer scoreGained;
    private UserProgramStatus status;
}
