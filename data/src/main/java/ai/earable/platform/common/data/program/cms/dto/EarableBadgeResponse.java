package ai.earable.platform.common.data.program.cms.dto;

import ai.earable.platform.common.data.program.common.model.Badge;
import lombok.*;

import java.util.UUID;

/**
 * Created by BinhNH on 08/08/2022
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@With
public class EarableBadgeResponse extends Badge {
    private UUID badgeId;
}
