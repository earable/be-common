package ai.earable.platform.common.data.program.user.dto;

import ai.earable.platform.common.data.program.common.enums.ProgramType;
import ai.earable.platform.common.data.program.common.model.Badge;
import ai.earable.platform.common.data.program.user.enums.UserBadgeStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.UUID;

/**
 * Created by NhuNH on 08/08/2022
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@With
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserBadgeResponse extends Badge {
    private UUID badgeId;
    private UUID userId;
    private UUID programId;
    private ProgramType programType;
    private UserBadgeStatus userBadgeStatus;
    protected Long receivedAt;
}
