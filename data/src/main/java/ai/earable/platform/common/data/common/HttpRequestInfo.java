package ai.earable.platform.common.data.common;

import ai.earable.platform.common.data.user.enums.UserLevel;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author DungNT
 * @date 04/07/2023
 */
@Data
@Builder
public class HttpRequestInfo {
    private String requestId;

    private long timestamp;
}
