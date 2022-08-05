package ai.earable.platform.common.data.cassandra;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.cassandra.core.mapping.Column;

import java.time.Instant;

/**
 * @author Hungnv
 * @date 30/05/2022
 */
@Data
public class BaseEntity {
    @LastModifiedDate
    @Column("updated_at")
    private Instant updatedAt;
    @CreatedDate
    @Column("created_at")
    private Instant createdAt;
}
