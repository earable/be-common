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
@Table(value = "user_contest_score")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserContestScore {
    @PrimaryKeyColumn(name = "contest_id", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    private UUID contestId;

    @PrimaryKeyColumn(name = "user_full_name", type = PrimaryKeyType.PARTITIONED, ordinal = 1)
    private String userFullName;

    @PrimaryKeyColumn(name = "user_id", type = PrimaryKeyType.PARTITIONED, ordinal = 2)
    private UUID userId;

    private int score;

    @Column("previous_day_contest_ranking")
    private int previousDayContestRanking;

    @Column("contest_ranking")
    private int contestRanking;

    @Column("user_avatar")
    private String userAvatar;
}
