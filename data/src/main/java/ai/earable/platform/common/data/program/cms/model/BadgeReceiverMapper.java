package ai.earable.platform.common.data.program.cms.model;

import ai.earable.platform.common.data.program.cms.enums.Receiver;
import lombok.*;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Data
@Builder
@UserDefinedType
@NoArgsConstructor
@AllArgsConstructor
@With
public class BadgeReceiverMapper {
    private String badgeId;
    private Receiver receiver;
}
