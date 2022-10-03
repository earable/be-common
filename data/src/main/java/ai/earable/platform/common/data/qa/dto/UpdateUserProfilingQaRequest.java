package ai.earable.platform.common.data.qa.dto;

import ai.earable.platform.common.data.qa.enums.UserProfilingQAType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Hungnv
 * @date 16/06/2022
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserProfilingQaRequest {
    @NotNull(message = "questionType is required")
    UserProfilingQAType questionType;
    List<UserProfilingQARequest> answerUpdates;
}
