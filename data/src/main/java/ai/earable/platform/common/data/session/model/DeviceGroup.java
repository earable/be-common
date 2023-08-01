package ai.earable.platform.common.data.session.model;

import ai.earable.platform.common.data.cassandra.BaseEntity;
import ai.earable.platform.common.data.session.DeviceStatus;
import ai.earable.platform.common.data.session.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Table(value = "device_group")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceGroup extends BaseEntity implements Serializable {
    @PrimaryKeyColumn(value = "name", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    private String name;

    @Column(value = "device_batch")
    private String deviceBatch;

    @Column(value = "description")
    private UUID description;
}
