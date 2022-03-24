package ai.earable.platform.dis.data.dto;

import ai.earable.platform.dis.data.model.MatrixData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by BinhNH on 3/17/22
 */
@Getter
@Setter
@NoArgsConstructor
public class MatrixDataResponse {
    private String status;
    private MatrixData data;
}
