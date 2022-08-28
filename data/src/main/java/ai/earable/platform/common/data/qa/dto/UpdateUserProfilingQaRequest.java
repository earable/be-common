package ai.earable.platform.common.data.qa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    List<UserProfilingQARequest> answerUpdates;
}
