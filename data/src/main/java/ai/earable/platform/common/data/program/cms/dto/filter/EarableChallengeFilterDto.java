package ai.earable.platform.common.data.program.cms.dto.filter;

import ai.earable.platform.common.data.program.cms.enums.RepeatType;
import ai.earable.platform.common.data.program.cms.enums.ProgramStatus;
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
    private RepeatType type;
    private ProgramStatus status;
    private Long createFrom;
    private Long createTo;
}
