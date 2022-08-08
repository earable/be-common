package ai.earable.platform.common.data.program.user.dto;

import lombok.*;

@Deprecated

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@With
public class BadgeResponse {
    private String badgeId;
    private String title;
    private String icon;
}
