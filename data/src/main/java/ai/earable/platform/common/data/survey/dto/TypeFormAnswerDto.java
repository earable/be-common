package ai.earable.platform.common.data.survey.dto;

import ai.earable.platform.common.data.survey.model.SurveyAnswerChoice;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@With
public class TypeFormAnswerDto {
    private String type;

    private Integer number;

    private String text;

    private String fieldType;

    private SurveyAnswerChoice choice;

    private List<SurveyAnswerChoice> choices;
}
