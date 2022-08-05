package ai.earable.platform.common.data.program.user.model;

import ai.earable.platform.common.data.program.cms.enums.ActivityType;
import ai.earable.platform.common.data.program.cms.enums.TaskFrequency;
import ai.earable.platform.common.data.program.cms.enums.TimeOperator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.io.Serializable;

@Data
@Builder
@UserDefinedType
@NoArgsConstructor
@AllArgsConstructor
public class UserTask implements Serializable {
    private String taskId;
    private String name;
    private ActivityType activityType;
    private TimeOperator timeOperator;
    private TaskFrequency taskFrequency;
    private int frequency;
    private int frequencyNumber;
    private String time;
    private String startTime;
    private String endTime;
    private int durationTask;
    private int point;
    private Boolean done = false;
}
