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
public class RawDataFilesDTO implements Serializable {
    private List<RawDataFileDTO> data;
}
