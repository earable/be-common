package ai.earable.platform.common.data.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.With;
import lombok.experimental.Accessors;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
@Table(value = "group")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@With
public class Group {
    @PrimaryKeyColumn(name = "id", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    private UUID id;

    @Column("name")
    private String name;

    @Column("order")
    private Integer order;

    @Column("description")
    private String description;

    @Column("note")
    private String note;

    @Column("created_at")
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    @Column("updated_at")
    private Timestamp updatedAt = new Timestamp(System.currentTimeMillis());

}
