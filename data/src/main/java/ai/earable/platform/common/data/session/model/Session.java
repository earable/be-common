package ai.earable.platform.common.data.session.model;

import ai.earable.platform.common.data.feature.model.SessionMode;
import ai.earable.platform.common.data.session.SessionType;
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
@Table(value = "session_1")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Session implements Serializable {
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordinal = 0)
    private String userId;

    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordinal = 1)
    private String profileId;

    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordinal = 2)
    private String featureName;

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, ordinal = 3)
    private long startedTime;

    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordinal = 4)
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

    @Column(value = "type")
    private SessionType type;

    @Column(value = "duration")
    private Integer duration;

    @Column(value = "sleep_rating")
    private Integer sleepRating;

    @Column(value = "total_undefined_time")
    private Integer totalUndefinedTime;

    @Column(value = "total_undefined_percentage")
    private Float totalUndefinedPercentage;

    @Column(value = "end_session_by")
    private String endSessionBy;

    @Column(value = "deep_sleep_booting")
    private Integer deepSleepBooting;

    @Column(value = "battery_consumed")
    private Integer batteryConsumed;

    @Column(value = "time_to_fall_as_sleep")
    private Integer timeToFallAsSleep;

    @Column(value = "sleep_efficiency")
    private Float sleepEfficiency;

    @Column(value = "total_sleep_time")
    private Integer totalSleepTime;

    @Column(value = "deep_sleep_time")
    private Integer deepSleepTime;

    @Column(value = "rem_sleep_time")
    private Integer remSleepTime;

    @Column(value = "light_sleep_time")
    private Integer lightSleepTime;

    @Column(value = "session_ended_time")
    private Long sessionEndedTime;

    @Column(value = "is_data_processed")
    private Boolean isDataProcessed;

    @Column(value = "serial_no")
    private String serialNo;
}
