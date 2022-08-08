package ai.earable.platform.common.data.program.common.model;

import ai.earable.platform.common.data.program.common.enums.ProgramStatus;
import ai.earable.platform.common.data.program.common.enums.ProgramType;
import ai.earable.platform.common.data.program.common.enums.RepeatType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;

/**
 * Created by BinhNH on 08/08/2022
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Program {
    protected String name;
    protected String icon;
    @Column("type")
    protected RepeatType type;
    protected Integer duration;
    protected String information;
    @Column("short_description")
    protected String shortDescription;
    @Column("required_point")
    protected Integer requiredPoint;
    protected ProgramStatus status;
    protected ProgramType programType;
    @Column("start_date")
    protected Long startDate;
    @Column("end_date")
    protected Long endDate;
    protected String createBy;
    protected Long createAt;
}
