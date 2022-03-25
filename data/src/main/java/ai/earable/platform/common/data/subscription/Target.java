package ai.earable.platform.common.data.subscription;

import lombok.*;

/**
 * Created by BinhNH on 3/24/2022
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Target {
    /**
     * Name of the feature (FOCUS, SLEEP, MEDITATION…)
     */
    private String featureName;

    /**
     * ID of the user whose client wants to subscribe threshold or report.
     */
    private String userId;

    /**
     * The profileId whose client wants to subscribe threshold or report.
     * This profileId must belong to the userId above.
     */
    private String profileId;
}
