package ai.earable.platform.common.data.program.cms.model;

import ai.earable.platform.common.data.program.common.model.Task;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Data
@Builder
@ToString
@UserDefinedType
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class EarableTask extends Task {

}
