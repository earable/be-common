package ai.earable.platform.common.data.user.dto;

import ai.earable.platform.common.data.user.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Map;

/**
 * @author Hungnv
 * @date 02/06/2022
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileRequest {
    @Size(max = 100, message = "{USER_PROFILE_NAME_MAX_LENGTH_ERROR}")
    private String name;
    @Size(min = 6, max = 20, message = "{USER_PROFILE_PHONE_LENGTH_ERROR}")
    private String phone;
    private String address;
    private String country;
    @Size(max = 255, message = "{USER_PROFILE_OCCUPATION_LENGTH_ERROR}")
    private String occupation;
    private String dob;
    @Min(value = 1, message = "{USER_PROFILE_HEIGHT_ERROR}")
    private Integer height;
    @Min(value = 1, message = "{USER_PROFILE_WEIGHT_ERROR}")
    private Integer weight;
    private Gender gender;
    private String gender_1;
    private String restoreId;
    private Map<String, String> metaDataSetting;
}
