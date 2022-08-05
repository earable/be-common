package ai.earable.platform.common.data.program.cms.model;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.UUID;

@Data
@Builder
@UserDefinedType
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class EarableReward {
    private String title;
    private String description;
    private String icon;
}
