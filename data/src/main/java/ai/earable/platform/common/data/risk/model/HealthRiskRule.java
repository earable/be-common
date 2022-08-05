package ai.earable.platform.common.data.risk.model;

import ai.earable.platform.common.data.risk.enums.HealthRiskStatus;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.List;

@Data
@Builder
@UserDefinedType
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class HealthRiskRule {
    private HealthRiskStatus status;
    @Column("advice_explanation")
    private String adviceExplanation;
    @Column("advice_content")
    private List<String> adviceContent;
    private double threshold;
    private String title;
    private String body;
}
