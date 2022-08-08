package ai.earable.platform.common.data.program.user.dto;

import ai.earable.platform.common.data.program.common.enums.ProgramType;
import ai.earable.platform.common.data.program.common.enums.RepeatType;
import ai.earable.platform.common.data.program.user.enums.UserProgramState;
import ai.earable.platform.common.data.program.user.enums.UserProgramStatus;
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
public class UserProgramProcessResponse {
    private UUID programId;
    private String name;
    private String icon;
    private Integer participant;
    private RepeatType type;
    private Integer duration;
    private Long startDate;
    private Long endDate;
    private Integer point;
    private List<UserTaskResponse> tasks;
    private List<BadgeResponse> badges;
    private Long joinDate;
    private ProgramType programType;
    private UserProgramState state;
    private UserProgramStatus status;
}
