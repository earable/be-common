package ai.earable.platform.common.data.user.model;

import ai.earable.platform.common.data.cassandra.BaseEntity;
import ai.earable.platform.common.data.user.enums.UserStatus;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@Table(value = "ims_user")
public class AdminTable extends BaseEntity implements Serializable {

    @PrimaryKey("user_id")
    private UUID userId;

    @Column("user_name")
    private String username;

    @Column("email")
    private String email;

    @Column("password")
    private String password;

    @Column("last_password")
    private String lastPassword;

    @Column("locked_at")
    private Instant lockedAt;

    @Column("password_changed_at")
    private Timestamp passwordChangedAt;

    @Column("failed_attempt")
    private Integer failedAttempt;

    @Column("role_id")
    private UUID roleId;

    @Column("status")
    private UserStatus status;

}
