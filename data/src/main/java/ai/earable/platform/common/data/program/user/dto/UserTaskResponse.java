package ai.earable.platform.common.data.program.user.dto;

import ai.earable.platform.common.data.program.common.enums.TaskFrequency;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserTaskResponse implements Serializable {
    private String taskId;
    private String name;
    private TaskFrequency taskFrequency;
    private Integer frequency;
    private Integer frequencyNumber;
    private int point;
    private Boolean done;
}
