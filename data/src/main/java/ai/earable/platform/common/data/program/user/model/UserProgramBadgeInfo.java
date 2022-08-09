package ai.earable.platform.common.data.program.user.model;

import ai.earable.platform.common.data.program.common.model.Badge;
import lombok.*;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Data
@Builder
@UserDefinedType
@NoArgsConstructor
@AllArgsConstructor
@With
public class UserProgramBadgeInfo extends Badge {
    private String badgeId;
}
