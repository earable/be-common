package ai.earable.platform.common.data.user.model;

import ai.earable.platform.common.data.cassandra.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

/**
 * @author DungNT
 * @date 03/08/2023
 */
@Data
@Builder
@Table(value = "user_device_manager")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserDeviceManager extends BaseEntity {
    @PrimaryKey("user_id")
    private UUID userId;
    private String name;
}
