package ai.earable.platform.common.data.program.cms.model;

import ai.earable.platform.common.data.program.common.model.Badge;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.List;
import java.util.UUID;

@Builder
@Table(value = "badge")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@With
@AccessType(AccessType.Type.PROPERTY)
public class EarableBadge extends Badge {
    @PrimaryKeyColumn(name = "id", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    @Override
    public void setBadgeId(String badgeId){
        this.badgeId = badgeId;
    }

    private List<UUID> programId;
}
