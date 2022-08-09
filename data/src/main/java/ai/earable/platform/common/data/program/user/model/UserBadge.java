package ai.earable.platform.common.data.program.user.model;

import ai.earable.platform.common.data.program.common.enums.ProgramType;
import ai.earable.platform.common.data.program.common.model.Badge;
import ai.earable.platform.common.data.program.user.enums.UserBadgeStatus;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Data
@Builder
@Table(value = "user_badge")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserBadge extends Badge {
    @PrimaryKeyColumn(name = "user_id", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    private UUID userId;

    @PrimaryKeyColumn(name = "program_id", type = PrimaryKeyType.CLUSTERED, ordinal = 0)
    private UUID programId;

    @Column("badge_id")
    private String badgeId;

    @Column("program_type")
    private ProgramType programType;

    @Column("user_badge_status")
    private UserBadgeStatus userBadgeStatus;

    @Column("received_at")
    protected Long receivedAt;
}
