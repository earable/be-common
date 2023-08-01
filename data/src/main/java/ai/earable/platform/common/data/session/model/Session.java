package ai.earable.platform.common.data.session.model;

import ai.earable.platform.common.data.feature.model.SessionMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;

@Data
@Table(value = "sessions")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Session implements Serializable {
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    private String userId;

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, ordinal = 1)
    private String profileId;

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, ordinal = 2)
    private String featureName;

    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordinal = 3, ordering = Ordering.DESCENDING)
    private long startedTime;

    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordinal = 4, ordering = Ordering.DESCENDING)
    private String sessionId;

    private SessionMode mode;

    private String sessionSettingId;

    private String deviceId;

    private long endedTime;

    private long clientTimestamp;

    private String timezone;

    private String location;

    private Double locationLatitude;

    private Double locationLongitude;

    @CassandraType(type = CassandraType.Name.MAP, typeArguments = {CassandraType.Name.TEXT, CassandraType.Name.TEXT})
    private Map<String, String> metadata;

    @Column(value = "createddate")
    private Timestamp createddate;
}
