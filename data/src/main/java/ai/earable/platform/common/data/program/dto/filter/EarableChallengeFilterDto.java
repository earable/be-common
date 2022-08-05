package ai.earable.platform.common.data.program.dto.filter;

import ai.earable.platform.common.data.program.enums.ProgramRepeatType;
import ai.earable.platform.common.data.program.enums.ProgramStatus;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@With
public class EarableChallengeFilterDto {
    private UUID id;
    private String name;
    private ProgramRepeatType type;
    private ProgramStatus status;
    private Long createFrom;
    private Long createTo;
}
