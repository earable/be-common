package ai.earable.platform.common.data.program.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinContestRequest {
    private String contestId;
    private String confirm;
}
