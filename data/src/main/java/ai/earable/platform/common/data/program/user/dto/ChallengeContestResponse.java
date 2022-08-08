package ai.earable.platform.common.data.program.user.dto;

import ai.earable.platform.common.data.program.common.enums.ProgramType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Deprecated

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@With
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChallengeContestResponse {
    private UUID id;
    private String name;
    private Integer totalTasks;
    private Integer tasksCompleted;
    private String banner;
    private Double score;
    private String type;
    private Integer requiredPoint;
    private Instant createAt;
    private ProgramType programType;
}
