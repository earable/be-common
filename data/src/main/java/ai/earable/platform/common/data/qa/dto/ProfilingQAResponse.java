package ai.earable.platform.common.data.qa.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Hungnv
 * @date 02/06/2022
 */
@Data
@Builder
public class ProfilingQAResponse {
    private List<QAResponse> questions;
}
