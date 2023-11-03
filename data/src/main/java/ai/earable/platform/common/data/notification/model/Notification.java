package ai.earable.platform.common.data.notification.model;

import ai.earable.platform.common.data.cassandra.BaseEntity;
import ai.earable.platform.common.data.notification.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@Table(value = "notification")
@NoArgsConstructor
@AllArgsConstructor
public class Notification extends BaseEntity {
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    @Column("id")
    private UUID id;
    @Column("user_id")
    private UUID userId;
    @Column("device_ids")
    private List<String> deviceIds;
    @Column("type")
    private NotificationType type;
    @Column("item_detail_id")
    private String itemDetailId;
    private String title;
    private String body;
    private String description;
    @Column("is_pushed_success")
    private Boolean isPushedSuccess;
    @Column("is_read")
    private Boolean isRead;
}
