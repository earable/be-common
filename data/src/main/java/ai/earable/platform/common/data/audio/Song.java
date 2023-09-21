package ai.earable.platform.common.data.audio;

import ai.earable.platform.common.data.cassandra.BaseEntity;
import ai.earable.platform.common.data.user.enums.Language;
import ai.earable.platform.common.data.user.enums.LayerPreference;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@Table(value = "song")
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Song extends BaseEntity implements Serializable {
    @PrimaryKey
    private UUID id;

    private String name;

    private String image;

    private String artist;

    private String url;

    private String type;

    private String source;

    @Column("feature_name")
    private String featureName;

    private Long viewed;

    @Column("age_prefer_min")
    private int agePreferMin;

    @Column("age_prefer_max")
    private int agePreferMax;

    @Column("gender_prefer")
    private String genderPrefer;

    private String description;

    @Column("rating_index")
    private Double ratingIndex;

    @Column("rating_count")
    private Long ratingCount;

    private Long priority;

    private Long duration;

    @Column("duration_milliseconds")
    private Long durationMilliseconds;

    @Column("ear_5_star_count")
    private Long ear5StarCount;

    @Column("ear_4_star_count")
    private Long ear4StarCount;

    @Column("ear_3_star_count")
    private Long ear3StarCount;

    @Column("ear_2_star_count")
    private Long ear2StarCount;

    @Column("ear_1_star_count")
    private Long ear1StarCount;

    @Column("ear_rating_index")
    private Double earRatingIndex;

    private Long version;

    @Column("genre_of_music")
    private String genreOfMusic;

    @Column("created_by")
    private String createdBy;

    @Column("languages")
    private List<Language> languages;

    @Column("layer_preference")
    private LayerPreference layerPreference;

    private Double volume;

    private String versioning;

    @Column("system_admin_score")
    private Integer systemAdminScore;
}