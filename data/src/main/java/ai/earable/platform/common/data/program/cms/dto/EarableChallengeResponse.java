package ai.earable.platform.common.data.program.cms.dto;

import ai.earable.platform.common.data.program.enums.ProgramRepeatType;
import ai.earable.platform.common.data.program.enums.ProgramStatus;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@With
public class EarableChallengeResponse {
    private UUID id;
    private String name;
    private ProgramRepeatType type;
    private ProgramStatus status;
    private String createBy;
    private Long createAt;
}
