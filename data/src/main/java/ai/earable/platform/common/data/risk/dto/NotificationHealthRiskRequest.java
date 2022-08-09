package ai.earable.platform.common.data.risk.dto;

import ai.earable.platform.common.data.risk.enums.HealthRiskAlertTime;
import ai.earable.platform.common.data.risk.enums.HealthRiskSeverity;
import ai.earable.platform.common.data.risk.enums.HealthRiskType;
import ai.earable.platform.common.data.risk.enums.HealthRiskUnit;
import ai.earable.platform.common.data.risk.model.HealthRiskOccurrence;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationHealthRiskRequest {
    @NotNull(message = "profileId is required")
    private UUID profileId;
    @NotBlank(message = "deviceId is required")
    private String deviceId;
    @NotNull(message = "type is required")
    private HealthRiskType healthRiskType;
    @NotNull(message = "status is required")
    private HealthRiskSeverity healthRiskSeverity;
    private List<HealthRiskOccurrence> occurrences;
    @NotNull(message = "result is required")
    private Double result;
    @NotNull(message = "unit is required")
    private HealthRiskUnit unit;
    @NotNull(message = "alertTime is required")
    private HealthRiskAlertTime alertTime;
    @NotBlank(message = "title is required")
    private String title;
    @NotBlank(message = "body is required")
    private String body;
    @NotBlank(message = "description is required")
    private String description;
    @NotNull(message = "occurrenceAt is required")
    private Instant occurrenceAt;
}
