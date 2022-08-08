package ai.earable.platform.common.data.program.cms.model;

import ai.earable.platform.common.data.program.common.enums.Receiver;
import ai.earable.platform.common.data.program.common.model.Badge;
import lombok.*;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Data
@Builder
@UserDefinedType
@NoArgsConstructor
@AllArgsConstructor
@With
public class EarableProgramBadgeInfo extends Badge {
    private String badgeId;
    private Receiver receiver;
}
