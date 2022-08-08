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
    private String name;
    private String icon;
    @Column("type")
    private RepeatType type;
    private Integer duration;
    private String information;
    @Column("short_description")
    private String shortDescription;
    @Column("required_point")
    private Integer requiredPoint;
    private ProgramStatus status;
    private ProgramType programType;
    @Column("start_date")
    private Long startDate;
    @Column("end_date")
    private Long endDate;
    private String createBy;
    private Long createAt;
}
