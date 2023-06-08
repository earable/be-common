package ai.earable.platform.common.data.user.model;

import ai.earable.platform.common.data.cassandra.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.List;
import java.util.UUID;

/**
 * @author DungNT
 * @date 07/06/2023
 */
@Data
@Builder
@Table(value = "user_profiling_qa_2")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserProfilingQA1 extends BaseEntity {
    @PrimaryKey("id")
    private UUID id;
    @Column("user_id")
    private UUID userId;
    @Column("question_id")
    private UUID questionId;
    private List<ProfilingQAAnswer1> answer;
    @Column("group_id")
    private UUID groupId;
}
