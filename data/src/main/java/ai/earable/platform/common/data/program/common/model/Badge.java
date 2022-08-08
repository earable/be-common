package ai.earable.platform.common.data.program.common.model;

import ai.earable.platform.common.data.cassandra.BaseEntity;
import ai.earable.platform.common.data.program.common.enums.BenefitType;
import ai.earable.platform.common.data.program.common.enums.SessionPoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Badge implements Serializable {
    @PrimaryKeyColumn(name = "id", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    protected String badgeId;
    protected String title;
    protected String description;
    protected String icon;
    @Column("benefit_value")
    protected Double benefitValue;
    @Column("benefit_type")
    protected BenefitType benefitType;
    @Column("session_point")
    protected SessionPoint sessionPoint;
    @Column("benefit_effective_period")
    protected Integer benefitEffectivePeriod;
    protected Long createAt;
}
