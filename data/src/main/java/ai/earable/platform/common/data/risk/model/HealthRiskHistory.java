package ai.earable.platform.common.data.risk.model;

import ai.earable.platform.common.data.risk.enums.HealthRiskUnit;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@UserDefinedType
@NoArgsConstructor
@AllArgsConstructor
public class HealthRiskHistory implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;
    private Integer startTime;
    private Integer endTime;
    private Double result;
    private HealthRiskUnit unit;
}
