package ai.earable.platform.common.data.program.cms.model;

import ai.earable.platform.common.data.program.cms.enums.Receiver;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;


@Builder
@UserDefinedType
@NoArgsConstructor
@AllArgsConstructor
@With
public class EarableProgramBadgeInfo extends Badge {
    private Receiver receiver;
}
