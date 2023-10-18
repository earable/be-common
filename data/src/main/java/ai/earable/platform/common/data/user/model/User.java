package ai.earable.platform.common.data.user.model;

import ai.earable.platform.common.data.cassandra.BaseEntity;
import ai.earable.platform.common.data.user.enums.AccountType;
import ai.earable.platform.common.data.user.enums.RoleType;
import ai.earable.platform.common.data.user.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * @author Hungnv
 * @date 30/05/2022
 */
@Data
@Builder
@Table(value = "user")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class User extends BaseEntity {
    @PrimaryKey("user_id")
    private UUID userId;
    @Column("google_id")
    private String googleId;
    @Column("facebook_id")
    private String facebookId;
    @Column("apple_id")
    private String appleId;
    @Column("user_name")
    private String username;
    @Column("phone_no")
    private Long phoneNumber;
    private String password;
    private UserStatus status;
    private List<RoleType> roles;
    @Column("user_agent")
    private String userAgent;
    @Column("last_signed_in")
    private Timestamp lastSignedIn;
    @Column("last_changed_username_at")
    private Timestamp lastChangedUsernameAt;

    public String getUsernameId() {
        if (!StringUtils.isEmpty(getUsername())) {
            return getUsername();
        }
        if (getPhoneNumber() != null) {
            return getPhoneNumber().toString();
        }
        if (userId != null) {
            return userId.toString();
        }
        String socialId = !StringUtils.isEmpty(getFacebookId()) ? getFacebookId() : (!StringUtils.isEmpty(getGoogleId()) ? getGoogleId() : getAppleId());
        return socialId;
    }

    public AccountType getAccountType() {
        if (StringUtils.isEmpty(password)) {
            return AccountType.SOCIAL;
        } else {
            if (!StringUtils.isEmpty(username)) {
                return AccountType.EMAIL;
            }
            return AccountType.PHONE_NUMBER;
        }
    }
}
