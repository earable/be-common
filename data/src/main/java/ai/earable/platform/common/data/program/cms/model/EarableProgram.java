package ai.earable.platform.common.data.program.cms.model;

import ai.earable.platform.common.data.program.cms.enums.EarableProgramStatus;
import ai.earable.platform.common.data.program.common.model.Program;
import ai.earable.platform.common.data.program.common.model.Reward;
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
@Table(value = "earable_program")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@With
public class EarableProgram extends Program {
    @PrimaryKeyColumn(name = "id", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    private UUID programId;

    @Column("earable_program_status")
    protected EarableProgramStatus earableProgramStatus;

    @Column("badge_info_list")
    private List<EarableProgramBadgeInfo> earableProgramBadgeInfos;

    private List<Reward> rewards;

    @Column("earable_tasks")
    private List<EarableTask> earableTasks;
}
