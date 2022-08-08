package ai.earable.platform.common.data.program.common.model;

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
    @Column("repeat_type")
    protected RepeatType repeatType;
    protected Integer duration; //Used for CHALLENGE only
    protected String information;
    @Column("short_description")
    protected String shortDescription;
    @Column("required_point")
    protected Integer requiredPoint;
    protected ProgramType programType;
    @Column("start_date")
    protected Long startDate; //Used for CONTEST only
    @Column("end_date")
    protected Long endDate; //Used for CONTEST only
    protected String createdBy;
    protected Long createdAt;
}
