package ai.earable.platform.common.data.contact.dto;

import ai.earable.platform.common.data.contact.enums.ContactRelationship;
import lombok.*;

import javax.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateContactRequest {
    @NotBlank(message = "Name contact is required")
    @Size(max = 50, message = "Name size must be between 0 and 50")
    private String name;
    @NotNull(message = "Relationship is required")
    private ContactRelationship relationship;
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9+]{0,1}+[0-9]{2,14}$", message = "Invalid phone number format")
    @Size(max = 15, message = "Phone number size must be between 0 and 15")
    private String phoneNumber;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 255, message = "Email size must be between 0 and 255")
    private String email;
    @NotNull(message = "Favorite contact is required")
    private Boolean isFavorite;
}
