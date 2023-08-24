package ai.earable.platform.common.data.session.model;

import ai.earable.platform.common.data.cassandra.BaseEntity;
import ai.earable.platform.common.data.feature.model.SessionMode;
import ai.earable.platform.common.data.session.DeviceStatus;
import ai.earable.platform.common.data.session.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@Table(value = "device")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Device implements Serializable {
    @PrimaryKeyColumn(value = "serial_no", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    private String serialNo;

    @Column(value = "device_batch")
    private String deviceBatch;

    @Column(value = "user_id")
    private UUID userId;

    @Column(value = "history_user_ids")
    private List<UUID> historyUserIds;

    @Column(value = "user_name")
    private String userName;

    @Column(value = "history_user_names")
    private List<String> historyUserNames;

    @Column(value = "device_status")
    private DeviceStatus deviceStatus;

    @Column(value = "first_activated")
    private Timestamp firstActivated;

    @Column(value = "last_location_display")
    private String lastLocationDisplay;

    @Column(value = "last_ip")
    private String lastIp;

    @Column(value = "last_location_latitude")
    private Double lastLocationLatitude;

    @Column(value = "last_location_longitude")
    private Double lastLocationLongitude;

    @Column(value = "app_version")
    private String appVersion;

    @Column(value = "fw_version")
    private String fwVersion;

    @Column(value = "ml_version")
    private String mlVersion;

    @Column(value = "last_session_id")
    private String lastSessionId;

    @Column(value = "last_session_at")
    private Timestamp lastSessionAt;

    @Column(value = "device_group")
    private String deviceGroup;

    @Column(value = "current_pic_type")
    private UserType currentPicType;

    @Column(value = "current_pic_username")
    private String currentPicUsername;

    @Column(value = "current_pic_id")
    private UUID currentPicId;

    @Column(value = "pic_note")
    private String picNote;

    @Column("last_active_at")
    private Timestamp lastActiveAt;

    @Column("created_at")
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    @Column("updated_at")
    private Timestamp updatedAt = new Timestamp(System.currentTimeMillis());

    @Column("warranty_date")
    private LocalDate warrantyDate;

    @Column("last_handover")
    private Timestamp lastHandover;

    @Column("color")
    private String color;

    @Column("model")
    private String model;

}
