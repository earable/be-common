package ai.earable.platform.common.data.user.model;

import ai.earable.platform.common.data.cassandra.BaseEntity;
import ai.earable.platform.common.data.qa.enums.UserProfilingQAType;
import ai.earable.platform.common.data.user.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

/**
 * @author DungNT
 * @date 06/06/2023
 */
@Data
@Builder
@Table(value = "profiling_qa_group")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ProfilingQAGroup extends BaseEntity {
    @PrimaryKey("id")
    private UUID id;

    @Column("name")
    private String name;

    @Column("is_user_profile")
    private boolean isUserProfile;

    @Column("type")
    private UserProfilingQAType type;

    @Column("language")
    private Language language;

    @Column("origin_group_id")
    private UUID originGroupId;

    @Column("parent_id")
    private UUID parentId;

    @Column("order")
    private Integer order;
}
