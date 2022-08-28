package ai.earable.platform.common.data.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingResponse {
    private UUID id;
    private String instruction;
    private String image;
    private String step;
}
