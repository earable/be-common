package ai.earable.platform.common.data.program.common.model;

import ai.earable.platform.common.data.program.common.enums.ActivityType;
import ai.earable.platform.common.data.program.common.enums.TaskFrequency;
import ai.earable.platform.common.data.program.common.enums.TimeOperator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by BinhNH on 08/08/2022
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task implements Serializable {
    protected UUID taskId;
    protected String name;
    protected ActivityType activityType;
    protected TimeOperator timeOperator;
    protected TaskFrequency taskFrequency;
    protected int requiredFrequency;

    /**
     * This will be use when timeOperator is:
     * BEFORE or
     * AT_AROUND or
     * AFTER
     */
    protected Long time;

    /**
     * This will be use when timeOperator is:
     * BETWEEN
     */
    protected Long startTime;
    protected Long endTime;

    /**
     * This will be use when timeOperator is:
     * DURATION
     */
    protected int duration;

    /**
     * The bonus point for user when completed task
     */
    protected int point;
}
