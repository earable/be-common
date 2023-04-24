package ai.earable.platform.common.data.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RawDataFileDTO implements Serializable {
    private RawDataType rawDataType;
    private List<S3ResourceDTO> resources;
}
