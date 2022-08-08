package ai.earable.platform.common.data.program.user.model;

import ai.earable.platform.common.data.program.common.model.Badge;
import ai.earable.platform.common.data.program.user.enums.UserBadgeStatus;
import lombok.*;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Builder
@UserDefinedType
@NoArgsConstructor
@AllArgsConstructor
@With
public class UserProgramBadgeInfo extends Badge {
    private String badgeId;
    private UserBadgeStatus status;
}
