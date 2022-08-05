package ai.earable.platform.common.data.program.cms.dto;

import ai.earable.platform.common.data.program.cms.enums.ProgramRepeatType;
import ai.earable.platform.common.data.program.cms.enums.ProgramType;
import ai.earable.platform.common.data.program.cms.enums.ProgramStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@With
public class EarableProgramDetailResponse {
    private UUID id;
    private String name;
    private ProgramRepeatType programRepeatType;
    private ProgramStatus programStatus;
    private String icon;
    private List<EarableBadgeResponse> badge;
    private Integer requiredPoint;
    private ProgramType programType;
}
