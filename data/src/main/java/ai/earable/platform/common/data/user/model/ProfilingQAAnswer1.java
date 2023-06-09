package ai.earable.platform.common.data.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.io.Serializable;

@Data
@Builder
@UserDefinedType
@NoArgsConstructor
@AllArgsConstructor
public class ProfilingQAAnswer1 implements Serializable {
    private String answerId;
    private String answerContent;
    private Boolean inputable;
}
