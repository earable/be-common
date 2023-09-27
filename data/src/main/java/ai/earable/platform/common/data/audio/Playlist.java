package ai.earable.platform.common.data.audio;

import ai.earable.platform.common.data.cassandra.BaseEntity;
import ai.earable.platform.common.data.constant.AudioPurpose;
import ai.earable.platform.common.data.constant.PlaylistType;
import ai.earable.platform.common.data.user.enums.Language;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@Table(value = "playlist")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Playlist extends BaseEntity implements Serializable {

    @PrimaryKey
    private UUID id;

    private String name;

    private String description;

    private String image;

    @Column("url_preview")
    private String urlPreview;

    private PlaylistType type;

    @CreatedBy
    @Column("created_by")
    private String createdBy;

    @Column("languages")
    private List<Language> languages;

    @Column("purposes")
    private List<AudioPurpose> purposes;
}
