package ai.earable.platform.common.data.user.dto;

import ai.earable.platform.common.data.user.enums.UserLevel;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Hungnv
 * @date 05/06/2022
 */
@Data
public class UserLevelRequest {
    @NotNull(message = "userLevel is required")
    private UserLevel userLevel;
}
