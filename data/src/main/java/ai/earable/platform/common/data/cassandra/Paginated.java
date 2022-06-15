package ai.earable.platform.common.data.cassandra;

import ai.earable.platform.common.data.exception.EarableErrorCode;
import ai.earable.platform.common.data.exception.EarableException;
import lombok.Data;

/**
 *  * Copyright (c) 2022 Earable
 *  * Author: nhunh
 *  * Created: 3/23/22, 2:02 AM
 */
@Data
public class Paginated {
    public static final Integer DEFAULT_LIMIT = 10;
    private Integer limit;
    private String pagingState;

    public Integer getLimit() {
        return limit == null ? DEFAULT_LIMIT : limit;
    }

    public String getPagingState() {
        return pagingState;
    }

    public void validate() {
        if (this.getLimit() < 1 || this.getLimit() > 100) {
            throw new EarableException(400, EarableErrorCode.PARAM_INVALID, "timezone");
        }
    }
}