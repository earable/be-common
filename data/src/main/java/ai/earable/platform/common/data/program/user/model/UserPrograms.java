package ai.earable.platform.common.data.program.user.model;

import ai.earable.platform.common.data.program.common.model.Program;
import ai.earable.platform.common.data.program.common.model.Reward;
import ai.earable.platform.common.data.program.user.enums.UserProgramState;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@Table(value = "user_programs")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserPrograms extends Program {
    @PrimaryKeyColumn(name = "user_id", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    private UUID userId;

    @PrimaryKeyColumn(name = "program_id", type = PrimaryKeyType.CLUSTERED, ordinal = 0)
    private UUID programId;

    @ToString.Exclude
    @Column("badge_info_list")
    private List<UserProgramBadgeInfo> userProgramBadgeInfos;

    @ToString.Exclude
    private List<Reward> rewards;

    @ToString.Exclude
    @Column("user_tasks")
    private List<UserTask> userTasks;

    @Column("user_program_state")
    private UserProgramState userProgramState;

    @Column("in_use")
    private boolean inUse;

    public String toString() {
        return "UserPrograms";
    }
}