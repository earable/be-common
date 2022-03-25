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

    public DataJob(){}

    public DataJob(String id, String featureName, List<String> userIds, List<String> profileIds, DataJobCriteria criteria, List<ReportInfo> reports) {
        this.id = id;
        this.featureName = featureName;
        this.userIds = userIds;
        this.profileIds = profileIds;
        this.criteria = criteria;
        this.reports = reports;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public List<String> getProfileIds() {
        return profileIds;
    }

    public void setProfileIds(List<String> profileIds) {
        this.profileIds = profileIds;
    }

    public DataJobCriteria getCriteria() {
        return criteria;
    }

    public void setCriteria(DataJobCriteria criteria) {
        this.criteria = criteria;
    }

    public List<ReportInfo> getReports() {
        return reports;
    }

    public void setReports(List<ReportInfo> reports) {
        this.reports = reports;
    }

    public static class ReportInfo {
        private URI href;
        private Date readyTime;
        private Date expiryTime;
        private int fileSize;
    }
}
