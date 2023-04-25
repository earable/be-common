package ai.earable.platform.common.data.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class S3ResourceDTO {
    private String fileName;
    private String url;
}
