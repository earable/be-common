package ai.earable.platform.common.data.cassandra;

import com.datastax.driver.core.utils.Bytes;
import lombok.Data;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Objects;

@Data
public class Page<T> {
    private final Integer count;
    private final List<T> content;
    private final int pageNumber;
    private final Boolean hasNext;
    private final int limit;

    public Page(final Slice<T> slice) {
        this.content = slice.getContent();
        this.count = content.size();
        this.hasNext = slice.hasNext();
        this.pageNumber = slice.getNumber();
        this.limit = slice.getPageable().getPageSize();
    }

    public int getPageNumber() {
        return pageNumber;
    }
}
