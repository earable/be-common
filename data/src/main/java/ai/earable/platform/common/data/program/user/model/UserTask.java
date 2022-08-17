package ai.earable.platform.common.data.program.user.model;

import ai.earable.platform.common.data.program.common.model.FrequencyStatus;
import ai.earable.platform.common.data.program.common.model.Task;
import lombok.*;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Data
@Builder
@UserDefinedType
@NoArgsConstructor
@AllArgsConstructor
@With
public class UserTask extends Task {
    protected FrequencyStatus frequencyStatus;
}


