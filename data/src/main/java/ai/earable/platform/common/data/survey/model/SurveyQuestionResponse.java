package ai.earable.platform.common.data.survey.model;

import ai.earable.platform.common.data.survey.model.SurveyQuestionFieldChoice;
import lombok.*;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.List;
import java.util.UUID;


@Data
@Table(value = "question_response")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SurveyQuestionResponse {
    @PrimaryKeyColumn(name = "survey_id", type = PrimaryKeyType.CLUSTERED, ordinal = 0)
    private UUID surveyId;

    @PrimaryKeyColumn(name = "question_id", type = PrimaryKeyType.PARTITIONED, ordinal = 1)
    private String questionId;

    private String ref;

    private String type;

    private String title;

    @Column(value = "allow_multiple_selections")
    private Boolean allowMultipleSelections = null;

    private List<SurveyQuestionFieldChoice> choices = null;

    private String answerType;

    private Integer answerNumber;

    private String answerText;

    private SurveyAnswerChoice answer = null;

    private List<SurveyAnswerChoice> answers = null;
}
