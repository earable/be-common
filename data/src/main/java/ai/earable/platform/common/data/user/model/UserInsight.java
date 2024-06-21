package ai.earable.platform.common.data.user.model;

import ai.earable.platform.common.data.cassandra.BaseEntity;
import ai.earable.platform.common.data.user.enums.Gender;
import ai.earable.platform.common.data.user.enums.Language;
import ai.earable.platform.common.data.user.enums.ProfileProgress;
import ai.earable.platform.common.data.user.enums.UserLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * @author DungNT
 * @date 18/08/2022
 */
@Data
@Builder
@Table(value = "user_insight")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserInsight extends BaseEntity {
    @PrimaryKey("user_id")
    private UUID userId;

    @Column(value = "sleep_session_count")
    private Integer sleepSessionCount;

    @Column(value = "nap_session_count")
    private Integer napSessionCount;

    @Column(value = "focus_session_count")
    private Integer focusSessionCount;

    @Column(value = "total_boosts")
    private Integer totalBoosts;

    @Column(value = "last_session_at")
    private Timestamp lastSessionAt;

    @Column(value = "first_session_at")
    private Timestamp firstSessionAt;
    
    @Column(value = "timezone_offset")
    private Integer timezoneOffset;
}
