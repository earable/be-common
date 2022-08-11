package ai.earable.platform.common.data.user.model;

import ai.earable.platform.common.data.cassandra.BaseEntity;
import ai.earable.platform.common.data.user.enums.UserLevel;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@Builder
@Table(value = "level")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Level extends BaseEntity {
    @PrimaryKeyColumn(name = "name", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    private UserLevel name;
    private Double score;
}
