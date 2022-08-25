package ai.earable.platform.common.data.cassandra;

import com.datastax.oss.driver.api.core.ConsistencyLevel;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.Statement;
import com.datastax.oss.driver.internal.core.cql.DefaultSimpleStatement;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value(value = "${earable.cassandra.consistency.level:QUORUM}")
    private ConsistencyLevel consistencyLevel;

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
        return ps.bind().setConsistencyLevel(consistencyLevel);
    }

    /**
     * Initialize the {@link SimpleStatement} with configured Consistency level
     */
    public SimpleStatement newStatement(@NonNull String cqlQuery){
        return SimpleStatement.newInstance(cqlQuery).setConsistencyLevel(consistencyLevel);
    }

    /**
     * Initialize the {@link SimpleStatement} with configured Consistency level
     */
    public SimpleStatement newStatement(@NonNull String cqlQuery, @NonNull Object... positionalValues){
        return SimpleStatement.newInstance(cqlQuery, positionalValues).setConsistencyLevel(consistencyLevel);
    }

    /**
     * Initialize the {@link SimpleStatement} with configured Consistency level
     */
    public SimpleStatement newStatement(@NonNull String cqlQuery, @NonNull Map<String, Object> namedValues){
        return SimpleStatement.newInstance(cqlQuery, namedValues).setConsistencyLevel(consistencyLevel);
    }

    /**
     * Initialize the {@link QuorumStatement} with Consistency level = QUORUM
     */
    public QuorumStatement newQuorumStatement(@NonNull String cqlQuery){
        return new QuorumStatement(cqlQuery);
    }

    /**
     * Initialize the {@link QuorumStatement} with Consistency level = QUORUM
     */
    public QuorumStatement newQuorumStatement(@NonNull String cqlQuery, @NonNull Object... positionalValues){
        return new QuorumStatement(cqlQuery, positionalValues);
    }

    /**
     * Initialize the {@link QuorumStatement} with Consistency level = QUORUM
     */
    public QuorumStatement newQuorumStatement(@NonNull String cqlQuery, @NonNull Map<String, Object> namedValues){
        return new QuorumStatement(cqlQuery, namedValues);
    }
}
