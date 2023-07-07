package ai.earable.platform.common.data.devicetracking;

import ai.earable.platform.common.data.cassandra.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Data
@Builder
@Table(value = "app_fw_version_info")
@NoArgsConstructor
@AllArgsConstructor
public class AppFwVersionInfo extends BaseEntity {
    @PrimaryKeyColumn(name = "app_version_id", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    private UUID appVersionId;

    @PrimaryKeyColumn(name = "fw_version_id", type = PrimaryKeyType.CLUSTERED, ordinal = 1)
    private UUID fwVersionId;
}
