package ai.earable.platform.common.data.subscription.dto;

import ai.earable.platform.common.data.subscription.DataNotificationFilter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by BinhNH on 3/24/2022
 */
@Getter
@Setter
@NoArgsConstructor
public class DataSubscriptionRequest extends SubscriptionRequest<DataNotificationFilter> {

}
