package ai.earable.platform.common.data.monitoring;

import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * Created by BinhNH on 3/24/2022
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataJobCriteria {
    private List<String> metrics;
    private List<String> expressions;
    private int collectionPeriod;
    private int reportingPeriod;
    private Date reportingBoundary;
}
