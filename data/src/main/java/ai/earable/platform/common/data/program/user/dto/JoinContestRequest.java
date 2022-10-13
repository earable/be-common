package ai.earable.platform.common.data.program.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinContestRequest {
    private UUID contestId;
    private String confirm;
}
