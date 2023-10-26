package ai.earable.platform.common.data.session.model;

import ai.earable.platform.common.data.cassandra.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@Builder
@Table(value = "session_detail")
@NoArgsConstructor
@AllArgsConstructor
public class SessionDetail extends BaseEntity {
    @PrimaryKeyColumn(value = "session_id", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    private String sessionId;

    @Column(value = "sleep_rating")
    private Integer sleepRating;

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

    @Column(value = "time_to_fall_as_leep")
    private Integer timeToFallAsleep;

    @Column(value = "started_time")
    private Long startedTime;

    @Column(value = "ended_time")
    private Long endedTime;

    @Column(value = "avg_heart_rate")
    private Integer avgHeartRate;

    @Column(value = "avg_spo2")
    private Integer avgSPO2;

    @Column(value = "deep_sleep_booting")
    private Integer deepSleepBooting;

    @Column(value = "year")
    private Integer year;

    @Column(value = "month_of_year")
    private Integer monthOfYear;

    @Column(value = "week_of_year")
    private Integer weekOfYear;

    @Column(value = "day_of_year")
    private Integer dayOfYear;
}
