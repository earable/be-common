package ai.earable.platform.common.data.user.dto;

import ai.earable.platform.common.data.user.enums.UserLevel;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author DungNT
 * @date 21/06/2023
 */
@Data
public class UserTutorialStatusRequest {
    @NotNull(message = "userLevel is required")
    private Integer tutorialStatus;
}
