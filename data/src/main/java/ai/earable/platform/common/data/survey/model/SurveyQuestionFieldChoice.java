package ai.earable.platform.common.data.survey.model;

import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Data
@Builder
@UserDefinedType
@NoArgsConstructor
@AllArgsConstructor
public class SurveyQuestionFieldChoice {
    private String id;

    private String label;

    private String ref;
}
