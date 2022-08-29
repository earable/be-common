package ai.earable.platform.common.data.user.dto;

import ai.earable.platform.common.data.user.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author Hungnv
 * @date 02/06/2022
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileRequest {
    @Size(max = 255, message = "name must not be greater than 255 characters.")
    @Pattern(regexp = "^(?=\\s*\\S).*$", message = "name must be not empty.")
    private String name;
    @Size(min = 6, max = 20, message = "phone must be on range from 6 to 20 characters.")
    private String phone;
    private String address;
    @Size(max = 255, message = "occupation must not be greater than 255 characters.")
    private String occupation;
    private String dob;
    @Min(value = 1, message = "height must be greater than or equal to 1")
    private Integer height;
    @Min(value = 1, message = "weight must be greater than or equal to 1")
    private Integer weight;
    private Gender gender;
}
