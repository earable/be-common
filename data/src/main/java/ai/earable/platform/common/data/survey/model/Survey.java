package ai.earable.platform.common.data.survey.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.sql.Timestamp;
import java.util.UUID;


@Data
@Table(value = "survey")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Survey {
    @PrimaryKeyColumn(name = "id", type = PrimaryKeyType.CLUSTERED, ordinal = 0)
    private UUID id;

    @PrimaryKeyColumn(name = "user_id", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    private UUID userId;

    @PrimaryKeyColumn(name = "session_id", type = PrimaryKeyType.PARTITIONED, ordinal = 1)
    private String sessionId;

    @PrimaryKeyColumn(name = "form_Id", type = PrimaryKeyType.PARTITIONED, ordinal = 1)
    private String formId;

    @Column(value = "token")
    private String token;

    @Column(value = "created_at")
    private Timestamp createdAt;

    @Column(value = "submitted_at")
    private Timestamp submittedAt;

    @Column(value = "updated_at")
    private Timestamp updatedAt;
}
