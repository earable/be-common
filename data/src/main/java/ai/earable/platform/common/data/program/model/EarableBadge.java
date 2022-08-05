package ai.earable.platform.common.data.program.model;

import ai.earable.platform.common.data.program.enums.BenefitType;
import ai.earable.platform.common.data.program.enums.Receiver;
import ai.earable.platform.common.data.program.enums.SessionPoint;
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
@Table(value = "badge")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@With
public class EarableBadge implements Serializable {
    @PrimaryKeyColumn(name = "id", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    private String badgeId;
    private String title;
    private String description;
    private String icon;
    @Column("benefit_value")
    private Double benefitValue;
    @Column("benefit_type")
    private BenefitType benefitType;
    @Column("session_point")
    private SessionPoint sessionPoint;
    @Column("benefit_effective_period")
    private Integer benefitEffectivePeriod;
    private Receiver receiver;
    private Long createAt;
    private List<UUID> programId;
}
