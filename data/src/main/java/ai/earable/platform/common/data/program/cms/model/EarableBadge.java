package ai.earable.platform.common.data.program.cms.model;

import ai.earable.platform.common.data.program.common.model.Badge;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@Table(value = "earable_badge")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class EarableBadge extends Badge {
    @PrimaryKeyColumn(name = "id", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    private String badgeId;

    @Column("program_ids")
    private List<UUID> programIds;
}
