package ai.earable.platform.common.data.program.cms.dto.filter;

import ai.earable.platform.common.data.program.enums.ProgramRepeatType;
import ai.earable.platform.common.data.program.enums.ProgramStatus;
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
    private ProgramRepeatType type;
    private ProgramStatus status;
    private Long startDateFrom;
    private Long startDateTo;
    private Long endDateFrom;
    private Long endDateTo;
    private Long createFrom;
    private Long createTo;
}
