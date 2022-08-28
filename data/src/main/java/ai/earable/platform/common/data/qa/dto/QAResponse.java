package ai.earable.platform.common.data.qa.dto;

import ai.earable.platform.common.data.qa.enums.QuestionType;
import lombok.Data;

import java.util.List;
import java.util.UUID;

/**
 * @author Hungnv
 * @date 03/06/2022
 */
@Data
public class QAResponse {
    private UUID id;
    private String question;
    private QuestionType questionType;
    private List<String> answer;
}
