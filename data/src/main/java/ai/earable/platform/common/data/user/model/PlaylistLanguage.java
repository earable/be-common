package ai.earable.platform.common.data.user.model;

import ai.earable.platform.common.data.cassandra.BaseEntity;
import ai.earable.platform.common.data.user.enums.Language;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@Table(value = "playlist_language")
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PlaylistLanguage extends BaseEntity implements Serializable {

    @PrimaryKeyColumn(name = "playlist_id", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    private UUID playlistId;

    @PrimaryKeyColumn(name = "language", ordinal = 1)
    private Language language;
}