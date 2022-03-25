package ai.earable.platform.common.data.subscription;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by BinhNH on 3/24/2022
 */
@Getter
@Setter
@NoArgsConstructor
public class DataNotificationFilter {
    private Target target;
    private NotificationType notificationType;
}
