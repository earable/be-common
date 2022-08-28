package ai.earable.platform.common.data.qa.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * @author Hungnv
 * @date 02/06/2022
 */
@Data
@NoArgsConstructor
public class UserProfilingQAResponse {
    private UUID questionId;
    private List<String> answer;
}
