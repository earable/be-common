package ai.earable.platform.common.data.user.dto;

import ai.earable.platform.common.data.user.enums.Gender;
import ai.earable.platform.common.data.user.enums.ProfileProgress;
import ai.earable.platform.common.data.user.enums.UserLevel;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.UUID;

/**
 * @author Hungnv
 * @date 02/06/2022
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@With
public class UserProfileResponse {
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
    private String avatar;
    private UserLevel userLevel;
    private Double score;
    private Double nextLevelScore;
    private UUID programId;
    private UUID badgeId;
    private String badgeIcon;
    private String badgeTitle;
    private Long dailyWear;
    private Double focusHours;
    private Double recoveryHours;
    private Long currentStreak;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long ranking;
    private Boolean focusFlag;
    private Boolean recoveryFlag;
    private ProfileProgress profileProgress;
    private String language;
}
