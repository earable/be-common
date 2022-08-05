package ai.earable.platform.common.data.program.cms.model;

import ai.earable.platform.common.data.program.cms.enums.ActivityType;
import ai.earable.platform.common.data.program.cms.enums.TaskFrequency;
import ai.earable.platform.common.data.program.cms.enums.TimeOperator;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.UUID;

@Data
@Builder
@ToString
@UserDefinedType
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class EarableTask {
    private UUID id;
    private String name;
    private ActivityType activityType;
    private TimeOperator timeOperator;
    private String startTime;
    private String endTime;
    private String time;
    private Integer durationTask;
    private Integer frequency;
    private TaskFrequency taskFrequency;
    private Integer point;
}
