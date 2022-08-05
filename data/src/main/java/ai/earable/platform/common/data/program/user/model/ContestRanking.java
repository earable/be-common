package ai.earable.platform.common.data.program.user.model;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.cassandra.core.mapping.Column;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@With
public class ContestRanking {
    @Column(value = "contest_id")
    private String contestId;

    private int score;

    @Column("user_full_name")
    private String userFullName;

    @Column(value = "user_id")
    private String userId;

    @Column("previous_day_contest_ranking")
    private int previousDayContestRanking;
}