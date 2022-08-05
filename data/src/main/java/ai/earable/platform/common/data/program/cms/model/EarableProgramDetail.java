package ai.earable.platform.common.data.program.cms.model;

import ai.earable.platform.common.data.program.cms.enums.ProgramStatus;
import ai.earable.platform.common.data.program.cms.enums.ProgramType;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.UUID;

@Data
@Builder
@UserDefinedType
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class EarableProgramDetail {
    private UUID id;
    private String name;
    private ProgramStatus status;
    private ProgramType programType;
    private String shortDescription;
    private Integer participant;
    private String icon;
}
