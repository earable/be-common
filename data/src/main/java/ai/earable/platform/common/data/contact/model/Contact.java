package ai.earable.platform.common.data.contact.model;

import ai.earable.platform.common.data.cassandra.BaseEntity;
import ai.earable.platform.common.data.contact.enums.ContactRelationship;
import ai.earable.platform.common.data.contact.enums.ContactStatus;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@Table(value = "contact")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@With
public class Contact extends BaseEntity {
    @PrimaryKeyColumn(name = "id", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    private UUID id;
    @Column("user_id")
    private UUID userId;
    private String name;
    private ContactRelationship relationship;
    @Column("phone_number")
    private String phoneNumber;
    private String email;
    private String image;
    @Column("is_favorite")
    private Boolean isFavorite;
    private ContactStatus status;
    @Column("create_at")
    private Instant createAt;
}