package ai.earable.platform.common.data.user.dto;

import ai.earable.platform.common.data.user.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author DungNT
 * @date 03/10/2023
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSurveyRequest {

    private Integer surveyType;
    private Integer surveyReminderCount;
    private Boolean surveyReminderDisabled;
}
