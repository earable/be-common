package ai.earable.platform.common.data.user.dto;

import ai.earable.platform.common.data.user.enums.Gender;
import ai.earable.platform.common.data.user.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author DungNT
 * @date 20/02/2023
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLanguageRequest {
    private Language language;
}
