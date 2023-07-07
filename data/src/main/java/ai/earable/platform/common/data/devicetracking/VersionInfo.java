package ai.earable.platform.common.data.devicetracking;

import ai.earable.platform.common.data.cassandra.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@Table(value = "version_info")
@NoArgsConstructor
@AllArgsConstructor
public class VersionInfo extends BaseEntity implements Comparable<VersionInfo> {
    @PrimaryKeyColumn(name = "id", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    private UUID id;
    @Column("name")
    private String name;
    @Column("version")
    private String version;
    @Column("type")
    private VersionType type;
    @Column("url")
    private String url;
    @Column("release_note")
    private List<String> releaseNote;
    @Column("required")
    private boolean required;
    @Column("no_of_supported_versions")
    private Integer noOfSupportedVersions;

    @Override
    public int compareTo(VersionInfo v) {
        if (v == null)
            return 1;
        String[] thisVersions = this.getVersion().replace(this.getName() + "_", "").split("\\.");
        String[] thatVersions = v.getVersion().replace(this.getName() + "_", "").split("\\.");
        int length = Math.max(thisVersions.length, thatVersions.length);
        for (int i = 0; i < length; i++) {
            int thisPart = i < thisVersions.length ? Integer.parseInt(thisVersions[i].replace("(", "").replace(")", "")) : 0;
            int thatPart = i < thatVersions.length ? Integer.parseInt(thatVersions[i].replace("(", "").replace(")", "")) : 0;
            if (thisPart < thatPart)
                return -1;
            if (thisPart > thatPart)
                return 1;
        }
        return 0;
    }
}
