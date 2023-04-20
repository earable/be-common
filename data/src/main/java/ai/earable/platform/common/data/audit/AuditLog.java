package ai.earable.platform.common.data.audit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.With;
import lombok.experimental.Accessors;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@With
public class AuditLog implements Serializable {
    private UUID userId;
    private UUID sessionId;
    private String apiUrlRequest;
    private String name;
    private Timestamp createdAt;
    private AuditType auditType;
}
