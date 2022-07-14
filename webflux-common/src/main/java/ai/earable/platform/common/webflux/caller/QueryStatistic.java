package ai.earable.platform.common.webflux.caller;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

/**
 * Created by BinhNH on 3/31/2022
 */
@Setter
@Getter
@Builder
@UserDefinedType
@NoArgsConstructor
@AllArgsConstructor
public class QueryStatistic {
    private String queryName;
    private String query;
}