package ai.earable.platform.common.data.program.dto;

import ai.earable.platform.common.data.program.enums.ProgramRepeatType;
import ai.earable.platform.common.data.program.enums.ProgramStatus;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@With
public class EarableContestResponse {
    private UUID id;
    private String name;
    private ProgramRepeatType type;
    private Long startDate;
    private Long endDate;
    private ProgramStatus status;
    private String createBy;
    private Long createAt;
}
