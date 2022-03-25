package ai.earable.platform.common.data.monitoring;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;

import java.net.URI;
import java.util.Date;
import java.util.List;

/**
 * Created by BinhNH on 3/24/2022
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeName("DataJob")
public class DataJob {
    @JsonProperty("_id")
    private String id;
    private String featureName;
    private String userIds;
    private List<String> profileIds;
    private DataJobCriteria criteria;
    private List<ReportInfo> reports; //TODO: Handle limiting number of report links here.

    public static class ReportInfo {
        private URI href;
        private Date readyTime;
        private Date expiryTime;
        private int fileSize;
    }
}
