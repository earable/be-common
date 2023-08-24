package ai.earable.platform.common.data.session.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by DungNT on 01/08/2023
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {

    private String status;

    private String country;

    private String regionName;

    private String display;

    private String city;

    private Double lat;

    private Double lon;
}
