package ai.earable.platform.common.data.monitoring;

import lombok.*;

/**
 * Created by BinhNH on 3/26/2022
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatisticMetric {
    private String name;
    private String expression;
}
