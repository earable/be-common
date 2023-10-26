package ai.earable.platform.common.data.user.model;

import ai.earable.platform.common.data.cassandra.BaseEntity;
import ai.earable.platform.common.data.qa.enums.QuestionType;
import ai.earable.platform.common.data.qa.enums.UserProfilingQAType;
import ai.earable.platform.common.data.user.enums.Language;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.List;
import java.util.UUID;

/**
 * @author DungNT
 * @date 25/09/2023
 */
@Data
@Builder
@Table(value = "profiling_qa_3")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ProfilingQA2 extends BaseEntity {
    @PrimaryKey("id")
    private UUID id;
    private String question;
    @Column("question_en")
    private String questionEn;
    @Column("origin_question_id")
    private UUID originQuestionId;
    @Column("question_type")
    private QuestionType questionType;
    private UserProfilingQAType type;
    private List<ProfilingQAAnswer2> answer;
    private Language language;
    @Column("group_id")
    private UUID groupId;
    @Column("order")
    private Integer order;
    private String title;
    @Column("image_url")
    private String imageUrl;
}
