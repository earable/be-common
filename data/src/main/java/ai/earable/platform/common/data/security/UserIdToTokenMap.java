package ai.earable.platform.common.data.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by BinhNH on 7/09/22
 * This object will be used in case client send writing data request to BE.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserIdToTokenMap {
    private String userId;
    private String token;
    private String email;
    private Long phoneNumber;
    private List<String> roles;
}
