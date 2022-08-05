package ai.earable.platform.common.data.program.cms.model;

import ai.earable.platform.common.data.program.enums.ProgramRepeatType;
import ai.earable.platform.common.data.program.enums.ProgramStatus;
import ai.earable.platform.common.data.program.enums.ProgramType;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@Table(value = "program")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@With
public class EarableProgram implements Serializable {
    @PrimaryKeyColumn(name = "id", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    private UUID id;
    private String name;
    private String icon;
    @Column("type")
    private ProgramRepeatType type;
    private Integer duration;
    private String information;
    @Column("short_description")
    private String shortDescription;
    @Column("required_point")
    private Integer requiredPoint;
    private ProgramStatus status;
    private ProgramType programType;
    @Column("start_date")
    private Long startDate;
    @Column("end_date")
    private Long endDate;
    private String createBy;
    private Long createAt;
    private List<BadgeProgram> earableBadges; //TODO: Long & Trong change to Map<badgeId,receiver>
    private List<EarableReward> earableRewards;
    private List<EarableTask> earableTasks;
}
