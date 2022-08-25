package ai.earable.platform.common.data.cassandra;

import com.datastax.oss.driver.api.core.ConsistencyLevel;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by BinhNH on 25/08/2022
 */
@Component
public class PreparedStatementStore {
    private final Map<String, PreparedStatement> preparedStatementMap = new ConcurrentHashMap<>();

    @Autowired
    private CqlSession cqlSession;

    /**
     * Get prepared statement existed in store
     * If not existed, prepare new statement and store
     */
    public BoundStatement getStatement(String cql) {
        PreparedStatement ps = preparedStatementMap.get(cql);
        if (ps == null) {
            ps = cqlSession.prepare(cql);
            preparedStatementMap.put(cql, ps);
        }
        return ps.bind();
    }
}
