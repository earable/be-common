package ai.earable.platform.common.data.monitoring;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Created by BinhNH on 3/24/2022
 */
@Getter
@Setter
@NoArgsConstructor
public class DataJobCriteria {
    private List<String> metrics;
    private List<String> expressions;
    private int collectionPeriod;
    private int reportingPeriod;
    private Date reportingBoundary;
}
