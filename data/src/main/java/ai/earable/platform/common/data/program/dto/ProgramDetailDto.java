package ai.earable.platform.common.data.program.dto;

import ai.earable.platform.common.data.program.ProgramType;
import ai.earable.platform.common.data.program.ProgramStatus;
import ai.earable.platform.common.data.program.ProgramRepeatType;
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
public class ProgramDetailDto {
    private UUID id;
    private String name;
    private ProgramRepeatType programRepeatType;
    private ProgramStatus programStatus;
    private String icon;
    private List<BadgeDto> badge;
    private Integer requiredPoint;
    private ProgramType programType;
}
