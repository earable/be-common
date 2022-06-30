package ai.earable.platform.common.data.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by BinhNH on 6/30/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBestRecordsRequest {
    private Long numOfDailyIncrement;
    private Long numOfStreakIncrement;
    private Double usedHours;
    private String featureName;
    private Boolean isFirstStreak;
}
