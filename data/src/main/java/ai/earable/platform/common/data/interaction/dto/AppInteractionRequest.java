package ai.earable.platform.common.data.interaction.dto;

import ai.earable.platform.common.data.ValidationUtils;
import ai.earable.platform.common.data.interaction.enums.ActionType;
import ai.earable.platform.common.data.interaction.enums.ElementType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppInteractionRequest {
    private UUID sessionId;
    private String sessionFeature;
    private UUID userId;
    private String startTime;
    private String startTimeUTC;
    private String elementName;
    private UUID elementId;
    private String elementGroup;
    private ElementType elementType;
    private ActionType action;
    private Map<String, String> scores;

    public void validate() {
        ValidationUtils.checkNull("sessionId", this.sessionId);
        ValidationUtils.checkBlank("sessionFeature", this.sessionFeature);
        ValidationUtils.checkBlank("elementName", this.elementName);
        ValidationUtils.checkBlank("elementGroup", this.elementGroup);
        ValidationUtils.checkNull("elementType", this.elementType);
        ValidationUtils.checkNull("action", this.action);
    }
}
