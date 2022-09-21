package ai.earable.platform.common.data.cassandra;

import ai.earable.platform.common.data.exception.EarableErrorCode;
import ai.earable.platform.common.data.exception.EarableException;
import lombok.Data;

/**
 *  * Copyright (c) 2022 Earable
 *  * Author: DungNT
 *  * Created: 09/20/22, 2:02 AM
 */
@Data
public class Paginated1 {
    public static final Integer DEFAULT_LIMIT = 10;
    public static final Integer DEFAULT_PAGE_INDEX = 0;
    private Integer limit;
    private Integer pagingState;

    public Integer getLimit() {
        return limit == null ? DEFAULT_LIMIT : limit;
    }

    public Integer getPagingState() {
        return pagingState == null ? DEFAULT_PAGE_INDEX : pagingState;
    }

    public void validate() {
        if (null != limit && (this.getLimit() < 1 || this.getLimit() > 100)) {
            throw new EarableException(400, EarableErrorCode.PARAM_INVALID, "limit");
        }
    }
}
