package ai.earable.platform.common.data.user.dto;

import ai.earable.platform.common.data.program.user.dto.UserBadgeResponse;
import lombok.*;

import java.util.List;
import java.util.UUID;

/**
 * Created by ManhDD on 09/08/2022
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@With
public class ScoringProfileResponse {
    private UUID userId;
    private String name;
    private String avatar;
    private int score;
    private Long dailyWear;
    private Double focusHours;
    private Double recoveryHours;
    private List<UserBadgeResponse> userBadgeResponses;
}
