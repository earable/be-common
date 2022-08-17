package ai.earable.platform.common.data.program.common.model;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

/**
 * Created by BinhNH on 17/08/2022
 */
@Data
@Builder
@UserDefinedType
@NoArgsConstructor
@AllArgsConstructor
@With
public class FrequencyStatusDetail {
    private int dayOfYear;
    private int weekOfYear;
    private int monthOfYear;
    private int year;
    private int currentFrequency = 0;
}
