package ai.earable.platform.common.data.program.user.model;

import ai.earable.platform.common.data.cassandra.BaseEntity;
import ai.earable.platform.common.data.program.common.enums.ProgramType;
import ai.earable.platform.common.data.program.common.enums.RepeatType;
import ai.earable.platform.common.data.program.cms.model.EarableReward;
import ai.earable.platform.common.data.program.user.enums.UserProgramState;
import ai.earable.platform.common.data.program.user.enums.UserProgramStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
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
public class UserPrograms extends BaseEntity {
    @PrimaryKeyColumn(name = "user_id", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    private UUID userId;

    @PrimaryKeyColumn(name = "program_id", type = PrimaryKeyType.CLUSTERED, ordinal = 0)
    private UUID programId;

    private String name;
    private String icon;
    private RepeatType type;
    private Integer duration;
    private Long startDate;
    private Long endDate;
    private String information;
    private String shortDescription;
    private Integer requiredPoint;

    private List<UserProgramBadgeInfo> userProgramBadgeInfos;

    private List<EarableReward> rewards;

    private List<UserTask> userTasks;

    private ProgramType programType;

    private UserProgramState state;

    private UserProgramStatus status;
}
