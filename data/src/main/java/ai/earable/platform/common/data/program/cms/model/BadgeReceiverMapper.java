package ai.earable.platform.common.data.program.cms.model;

import ai.earable.platform.common.data.program.common.enums.Receiver;
import lombok.*;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.UUID;

@Data
@Builder
@UserDefinedType
@NoArgsConstructor
@AllArgsConstructor
@With
public class BadgeReceiverMapper {
    private UUID badgeId;
    private Receiver receiver;
}
