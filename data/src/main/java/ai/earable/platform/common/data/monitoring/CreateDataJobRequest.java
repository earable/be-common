package ai.earable.platform.common.data.monitoring;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by BinhNH on 3/25/2022
 */
@Getter
@Setter
@NoArgsConstructor
public class CreateDataJobRequest {
    private String featureName;
    private List<String> userIds;
    private List<String> profileIds;
    private DataJobCriteria criteria;
    private FilterPolicy filterPolicy;
}
