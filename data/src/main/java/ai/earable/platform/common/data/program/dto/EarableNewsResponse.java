package ai.earable.platform.common.data.program.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@With
public class EarableNewsResponse {
    private String id;
    private String title;
    private String thumbnail;
    private String description;
    private String source;
}
