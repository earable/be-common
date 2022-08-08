package ai.earable.platform.common.data.program.cms.dto.filter;

import ai.earable.platform.common.data.program.common.enums.RepeatType;
import ai.earable.platform.common.data.program.common.enums.ProgramStatus;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@With
public class EarableContestFilterDto {
    private UUID id;
    private String name;
    private RepeatType type;
    private ProgramStatus status;
    private Long startDateFrom;
    private Long startDateTo;
    private Long endDateFrom;
    private Long endDateTo;
    private Long createFrom;
    private Long createTo;
}
