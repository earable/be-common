package ai.earable.platform.common.data.user.model;

import ai.earable.platform.common.data.cassandra.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.List;
import java.util.UUID;

/**
 * @author Hungnv
 * @date 02/06/2022
 */
@Data
@Builder
@Table(value = "user_profiling_qa")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserProfilingQA extends BaseEntity {
    @PrimaryKey("id")
    private UUID id;
    @Column("user_id")
    private UUID userId;
    @Column("question_id")
    private UUID questionId;
    private List<String> answer;
}
