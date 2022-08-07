package ai.earable.platform.common.data.program.cms.dto;

import ai.earable.platform.common.data.program.cms.enums.RepeatType;
import ai.earable.platform.common.data.program.cms.enums.ProgramType;
import ai.earable.platform.common.data.program.cms.enums.ProgramStatus;
import ai.earable.platform.common.data.program.cms.model.*;
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
    private RepeatType repeatType;
    private ProgramStatus status;
    private String icon;
    private Integer requiredPoint;
    private String information;
    private ProgramType programType;
    private List<Badge> badges;
    private List<EarableReward> earableRewards;
    private List<EarableTask> earableTasks;
    private String shortDescription;
    private Integer participant;
    private String createBy;
    private Long createAt;

    /**
     * Used for CONTEST only
     */
    private Long startDate;

    /**
     * Used for CONTEST only
     */
    private Long endDate;
}
