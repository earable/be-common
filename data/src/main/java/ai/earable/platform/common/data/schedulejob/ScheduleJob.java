package ai.earable.platform.common.data.schedulejob;

import ai.earable.platform.common.data.common.ScheduleJobStatus;
import ai.earable.platform.common.data.common.ScheduleJobType;
import ai.earable.platform.common.data.session.DeviceStatus;
import ai.earable.platform.common.data.session.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Table(value = "schedule_job")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleJob implements Serializable {
    @PrimaryKeyColumn(value = "id", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    private UUID id;

    @Column("schedule_job_type")
    private ScheduleJobType scheduleJobType;

    @Column("schedule_job_status")
    private ScheduleJobStatus scheduleJobStatus;

    @Column("created_at")
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    @Column("updated_at")
    private Timestamp updatedAt = new Timestamp(System.currentTimeMillis());


}
