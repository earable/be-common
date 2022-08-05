package ai.earable.platform.common.data.program.user.model;

import ai.earable.platform.common.data.cassandra.BaseEntity;
import ai.earable.platform.common.data.program.cms.enums.BenefitType;
import ai.earable.platform.common.data.program.cms.enums.ProgramType;
import ai.earable.platform.common.data.program.cms.enums.SessionPoint;
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
public class UserBadge extends BaseEntity {
    @PrimaryKeyColumn(name = "id", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    private UUID id;
    @Column("user_id")
    private UUID userId;
    @Column("program_id")
    private UUID programId;
    @Column("badge_id")
    private String badgeId;
    private UserBadgeStatus status;
    @Column("program_type")
    private ProgramType programType;

    private String title;
    private String description;
    private String icon;
    private Double benefitValue;
    private BenefitType benefitType;
    private SessionPoint sessionPoint;
    private Integer benefitEffectivePeriod;
    private long receiveAt;
}
