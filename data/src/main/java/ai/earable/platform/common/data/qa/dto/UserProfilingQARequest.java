package ai.earable.platform.common.data.qa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

/**
 * @author Hungnv
 * @date 02/06/2022
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfilingQARequest {
    @NotNull(message = "questionId is required")
    private UUID questionId;
    @NotEmpty(message = "answer is required")
    private List<String> answer;
}
