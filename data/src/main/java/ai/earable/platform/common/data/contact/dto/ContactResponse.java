package ai.earable.platform.common.data.contact.dto;

import ai.earable.platform.common.data.contact.enums.ContactRelationship;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContactResponse implements Comparable<ContactResponse> {
    private UUID id;
    private String name;
    private ContactRelationship relationship;
    private String phoneNumber;
    private String email;
    private String image;
    private Boolean isFavorite;
    private Instant createAt;

    @Override
    public int compareTo(ContactResponse o) {
        if (this.getRelationship().equals(ContactRelationship.DOCTOR) &&
                o.getRelationship().equals(ContactRelationship.DOCTOR)) {
            if (o.getIsFavorite()){
                return 1;
            }
            if (this.getIsFavorite()){
                return -1;
            }
            return this.getCreateAt().compareTo(o.getCreateAt());
        }
        if (!this.getRelationship().equals(ContactRelationship.DOCTOR) &&
                !o.getRelationship().equals(ContactRelationship.DOCTOR)) {
            if (o.getIsFavorite()){
                return 1;
            }
            if (this.getIsFavorite()){
                return -1;
            }
            return this.getCreateAt().compareTo(o.getCreateAt());
        }
        if (this.getRelationship().equals(ContactRelationship.DOCTOR) &&
                !o.getRelationship().equals(ContactRelationship.DOCTOR)) {
            return 1;
        }
        return -1;
    }
}
