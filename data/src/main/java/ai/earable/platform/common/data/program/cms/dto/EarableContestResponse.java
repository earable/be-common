package ai.earable.platform.common.data.program.cms.dto;

import ai.earable.platform.common.data.program.cms.enums.RepeatType;
import ai.earable.platform.common.data.program.cms.enums.ProgramStatus;
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
    private RepeatType type;
    private Long startDate;
    private Long endDate;
    private ProgramStatus status;
    private String createBy;
    private Long createAt;
}