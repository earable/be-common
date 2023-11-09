package ai.earable.platform.common.data.session.model;

import ai.earable.platform.common.data.session.DeviceStatus;
import ai.earable.platform.common.data.session.UserType;
import ai.earable.platform.common.data.user.enums.Language;
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
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Table(value = "color")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Color implements Serializable {
    @PrimaryKeyColumn(value = "id", type = PrimaryKeyType.CLUSTERED, ordinal = 0)
    private String id;

    @PrimaryKeyColumn(value = "language", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    private Language language;

    @Column(value = "name")
    private String name;

    @Column(value = "code")
    private String code;
}
