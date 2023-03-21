package ai.earable.platform.common.data.user.model;

import ai.earable.platform.common.data.cassandra.BaseEntity;
import ai.earable.platform.common.data.user.enums.Gender;
import ai.earable.platform.common.data.user.enums.Language;
import ai.earable.platform.common.data.user.enums.ProfileProgress;
import ai.earable.platform.common.data.user.enums.UserLevel;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

/**
 * @author Hungnv
 * @date 01/06/2022
 */
@Data
@Builder
@Table(value = "user_profile")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserProfile extends BaseEntity {
    @PrimaryKey("user_id")
    private UUID userId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String occupation;
    private String dob;
    private Integer height;
    private Integer weight;
    private Gender gender;
    @Column("user_level")
    private UserLevel userLevel;
    private String avatar;
    private Double score;
    @Column("daily_wear")
    private Long dailyWear;
    @Column("focus_hours")
    private Double focusHours;
    @Column("recovery_hours")
    private Double recoveryHours;
    @Column("current_streak")
    private Long currentStreak;
    @Column("old_streak")
    private Long oldStreak;
    @Column("focus_flag")
    private Boolean focusFlag;
    @Column("recovery_flag")
    private Boolean recoveryFlag;
    @Column("profile_progress")
    private ProfileProgress profileProgress;
    @Column("last_occur_session")
    private Long lastOccurSession;
    @Column("consecutive_day")
    private Integer consecutiveDay;
    @Column("language")
    private Language language;
}
