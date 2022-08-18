package ai.earable.platform.common.data.program.common.model;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.List;

/**
 * Created by BinhNH on 17/08/2022
 */
@Data
@Builder
@UserDefinedType
@NoArgsConstructor
@AllArgsConstructor
@With
public class FrequencyStatus {
    private boolean done = false;
    private List<FrequencyStatusDetail> frequencyStatusDetails;
}
