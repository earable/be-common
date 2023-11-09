package ai.earable.platform.common.data.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author DungNT
 * @date 03/10/2023
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVoiceCoachRequest {

    private Integer voiceCoachReminderCount;
    private Boolean voiceCoachReminderDisabled;
}
