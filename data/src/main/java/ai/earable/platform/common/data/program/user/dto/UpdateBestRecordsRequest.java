package ai.earable.platform.common.data.program.user.dto;

import lombok.Data;

@Data
public class UpdateBestRecordsRequest {
    private Long numOfDailyIncrement;
    private Long numOfStreakIncrement;
    private Double usedHours;
    private String featureName;
    private Boolean isFirstStreak;
}
