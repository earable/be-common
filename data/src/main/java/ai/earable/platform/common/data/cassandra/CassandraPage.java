package ai.earable.platform.common.data.cassandra;

import com.datastax.driver.core.utils.Bytes;
import lombok.Data;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Objects;

@Data
public class CassandraPage<T> {
    private final Integer count;
    private final List<T> content;
    private final String pagingState;
    private final Boolean hasNext;

    public CassandraPage(final Slice<T> slice) {
        this.content = slice.getContent();
        this.count = content.size();
        this.hasNext = slice.hasNext();
        this.pagingState = getPagingState(slice);
    }

    private static <T> String getPagingState(final Slice<T> slice) {
        if (slice.hasNext()) {
            CassandraPageRequest pageRequest = (CassandraPageRequest) slice.nextPageable();
            return Bytes.toRawHexString(Objects.requireNonNull(pageRequest.getPagingState()));
        } else {
            return null;
        }
    }

    public String getPagingState() {
        return pagingState;
    }
}
