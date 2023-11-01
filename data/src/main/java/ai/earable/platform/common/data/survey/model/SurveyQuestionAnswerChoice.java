package ai.earable.platform.common.data.survey.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Data
@Builder
@UserDefinedType
@NoArgsConstructor
@AllArgsConstructor
public class SurveyQuestionAnswerChoice {
    private String id;

    private String ref;

    private String label;
}
