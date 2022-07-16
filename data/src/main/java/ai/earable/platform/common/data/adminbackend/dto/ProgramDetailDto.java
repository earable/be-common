package ai.earable.platform.common.data.adminbackend.dto;

import ai.earable.platform.common.data.adminbackend.ProgramType;
import ai.earable.platform.common.data.adminbackend.Status;
import ai.earable.platform.common.data.adminbackend.Type;
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
    private Type type;
    private Status status;
    private String banner;
    List<BadgeDto> badge;
    private Integer requiredPoint;
    private ProgramType programType;
}
