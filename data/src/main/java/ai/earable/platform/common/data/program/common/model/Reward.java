package ai.earable.platform.common.data.program.common.model;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Data
@Builder
@UserDefinedType
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Reward {
    private String title;
    private String description;
    private String icon;
}
