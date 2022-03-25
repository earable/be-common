package ai.earable.platform.common.data.monitoring;

import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.NamingStrategy;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import com.datastax.oss.driver.api.mapper.annotations.PropertyStrategy;
import com.datastax.oss.driver.api.mapper.entity.naming.NamingConvention;

import java.net.URI;
import java.util.Date;
import java.util.List;

/**
 * Created by BinhNH on 3/24/2022
 */
@Entity
@PropertyStrategy(mutable = false)
@NamingStrategy(convention = NamingConvention.UPPER_SNAKE_CASE)
public class DataJob {
    @PartitionKey
    protected String id;
    private String featureName;
    private List<String> userIds;
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
