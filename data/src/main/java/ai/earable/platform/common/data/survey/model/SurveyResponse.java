package ai.earable.platform.common.data.survey.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;


@Data
@Table(value = "survey")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SurveyResponse {
    @PrimaryKeyColumn(name = "user_id", type = PrimaryKeyType.CLUSTERED, ordinal = 0)
    private UUID userId;

    @PrimaryKeyColumn(name = "session_id", type = PrimaryKeyType.PARTITIONED, ordinal = 1)
    private String sessionId;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
