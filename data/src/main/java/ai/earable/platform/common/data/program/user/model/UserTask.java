package ai.earable.platform.common.data.program.user.model;

import ai.earable.platform.common.data.program.common.model.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Builder
@UserDefinedType
@NoArgsConstructor
@AllArgsConstructor
@With
public class UserTask extends Task {
    protected int currentFrequency;
    protected boolean done = false;
}
