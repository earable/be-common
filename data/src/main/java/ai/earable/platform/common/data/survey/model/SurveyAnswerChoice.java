package ai.earable.platform.common.data.survey.model;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Data
@Builder
@UserDefinedType
@NoArgsConstructor
@AllArgsConstructor
public class SurveyAnswerChoice {
    private String id;

    private String label;
    
    private String ref;
}
