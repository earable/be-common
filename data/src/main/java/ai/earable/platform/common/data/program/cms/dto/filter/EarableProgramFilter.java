package ai.earable.platform.common.data.program.cms.dto.filter;

import ai.earable.platform.common.data.program.cms.enums.EarableProgramStatus;
import ai.earable.platform.common.data.program.common.enums.RepeatType;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@With
public class EarableProgramFilter {
    private UUID id;
    private String name;
    private RepeatType type;
    private EarableProgramStatus status;
    private Long startDateFrom;
    private Long startDateTo;
    private Long endDateFrom;
    private Long endDateTo;
    private Long createFrom;
    private Long createTo;
}
