package ai.earable.platform.common.data.subscription;

import lombok.*;

/**
 * Created by BinhNH on 3/24/2022
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataNotificationFilter {
    private Target target;
    private NotificationType notificationType;
}
