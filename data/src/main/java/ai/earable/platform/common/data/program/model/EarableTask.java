package ai.earable.platform.common.data.program.model;

import ai.earable.platform.common.data.program.enums.ActivityType;
import ai.earable.platform.common.data.program.enums.FrequencyTimeType;
import ai.earable.platform.common.data.program.enums.PrepositionTime;
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
    private PrepositionTime prepositionTime;
    private String startTime;
    private String endTime;
    private String time;
    private Integer durationTask;
    private Integer frequency;
    private FrequencyTimeType frequencyTimeType;
    private Integer point;
}
