package ai.earable.platform.common.data.program.user.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@With
public class UserBadgeResponse {
    private String id;
    private String title;
    private String icon;
    private Boolean isAchieved;
}
