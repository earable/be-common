package ai.earable.platform.common.data.program.user.model;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Data
@Builder
@Table(value = "user_social_score")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserSocialScore {
    @PrimaryKeyColumn(name = "social_key", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    private int socialKey; // Always = 0

    @PrimaryKeyColumn(name = "user_full_name", type = PrimaryKeyType.PARTITIONED, ordinal = 1)
    private String userFullName;

    @PrimaryKeyColumn(name = "user_id", type = PrimaryKeyType.PARTITIONED, ordinal = 2)
    private UUID userId;

    private int score;

    @Column("previous_day_social_ranking")
    private int previousDaySocialRanking;

    @Column("social_ranking")
    private int socialRanking;

    @Column("user_avatar")
    private String userAvatar;

}
