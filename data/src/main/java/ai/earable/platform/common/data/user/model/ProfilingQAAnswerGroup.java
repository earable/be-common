package ai.earable.platform.common.data.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@UserDefinedType
@NoArgsConstructor
@AllArgsConstructor
public class ProfilingQAAnswerGroup implements Serializable {
    private String groupName;
    private String groupDescription;
    private String groupId;
    private List<ProfilingQAAnswer1> answerList;
}
