package ai.earable.platform.common.data.cassandra;

import com.datastax.oss.driver.api.core.ConsistencyLevel;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.cassandra.ReactiveSession;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by BinhNH on 25/08/2022
 */
@Component
public class EarableStatementHelper {
    private final Map<String, PreparedStatement> preparedStatementMap = new ConcurrentHashMap<>();

    @Autowired
    private ReactiveSession reactiveSession;

    @Value(value = "${earable.cassandra.consistency.level:QUORUM}")
    private ConsistencyLevel consistencyLevel;

    /**
     * Get prepared statement existed in store
     * If not existed, prepare new statement and store
     *
     * Note 1:
     * - When you prepare the statement, Cassandra will parse the query string, cache the result and return a unique
     * identifier (the PreparedStatement object keeps an internal reference to that identifier).
     * - When you bind and execute a prepared statement, the driver will only send the identifier, which allows
     * Cassandra to skip the parsing phase
     * => So we should prepare only once, and cache the PreparedStatement in our application (thread-safe) to avoid
     * preparing multiple times with the same query string
     *
     * Note 2:
     * If you execute a query only once, a prepared statement is inefficient because it requires two roundtrips.
     * Consider a {@link SimpleStatement} or {@link QuorumStatement} instead (like below).
     */
    public Mono<BoundStatement> prepare(String cql) {
        PreparedStatement ps = preparedStatementMap.get(cql);
        if (ps == null) {
            return reactiveSession.prepare(cql).map(preparedStatement -> {
                preparedStatementMap.put(cql, preparedStatement);
                return preparedStatement.bind().setConsistencyLevel(consistencyLevel);
            });
        }
        return Mono.just(ps.bind().setConsistencyLevel(consistencyLevel));
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
