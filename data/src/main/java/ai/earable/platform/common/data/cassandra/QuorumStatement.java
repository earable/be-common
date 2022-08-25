package ai.earable.platform.common.data.cassandra;

import com.datastax.oss.driver.api.core.ConsistencyLevel;
import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.config.DriverExecutionProfile;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.metadata.Node;
import com.datastax.oss.driver.api.core.metadata.token.Token;
import com.datastax.oss.protocol.internal.util.collection.NullAllowingImmutableList;
import com.datastax.oss.protocol.internal.util.collection.NullAllowingImmutableMap;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;

import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by BinhNH on 25/08/2022
 */
public class QuorumStatement implements SimpleStatement{
    private final String query;
    private final List<Object> positionalValues;
    private final Map<CqlIdentifier, Object> namedValues;
    private final String executionProfileName;
    private final DriverExecutionProfile executionProfile;
    private final CqlIdentifier keyspace;
    private final CqlIdentifier routingKeyspace;
    private final ByteBuffer routingKey;
    private final Token routingToken;
    private final Map<String, ByteBuffer> customPayload;
    private final Boolean idempotent;
    private final boolean tracing;
    private final long timestamp;
    private final ByteBuffer pagingState;
    private final int pageSize;
    private final ConsistencyLevel consistencyLevel = ConsistencyLevel.QUORUM;
    private final ConsistencyLevel serialConsistencyLevel = ConsistencyLevel.QUORUM;
    private final Duration timeout;
    private final Node node;
    private final int nowInSeconds;

    public QuorumStatement(@NonNull String cqlQuery){
        this(cqlQuery, NullAllowingImmutableList.of(),
            NullAllowingImmutableMap.of(),
            (String)null,
            (DriverExecutionProfile)null,
            (CqlIdentifier)null,
            (CqlIdentifier)null,
            (ByteBuffer)null,
            (Token)null,
            NullAllowingImmutableMap.of(),
            (Boolean)null, false,
            Long.MIN_VALUE,
            (ByteBuffer)null,
            Integer.MIN_VALUE,
            ConsistencyLevel.QUORUM,
            ConsistencyLevel.QUORUM,
            (Duration)null,
            (Node)null,
            Integer.MIN_VALUE);
    }

    public QuorumStatement(@NonNull String cqlQuery, @NonNull List<Object> positionalValues){
        this(cqlQuery, positionalValues,
            NullAllowingImmutableMap.of(),
            (String)null,
            (DriverExecutionProfile)null,
            (CqlIdentifier)null,
            (CqlIdentifier)null,
            (ByteBuffer)null,
            (Token)null,
            NullAllowingImmutableMap.of(),
            (Boolean)null, false,
            Long.MIN_VALUE,
            (ByteBuffer)null,
            Integer.MIN_VALUE,
            ConsistencyLevel.QUORUM,
            ConsistencyLevel.QUORUM,
            (Duration)null,
            (Node)null,
            Integer.MIN_VALUE);
    }

    public QuorumStatement(@NonNull String cqlQuery, @NonNull Map<String, Object> namedValues){
        this(cqlQuery, NullAllowingImmutableList.of(),
                QuorumStatement.wrapKeys(namedValues),
                (String)null,
                (DriverExecutionProfile)null,
                (CqlIdentifier)null,
                (CqlIdentifier)null,
                (ByteBuffer)null,
                (Token)null,
                NullAllowingImmutableMap.of(),
                (Boolean)null,
                false,
                Long.MIN_VALUE,
                (ByteBuffer)null,
                Integer.MIN_VALUE,
                ConsistencyLevel.QUORUM,
                ConsistencyLevel.QUORUM,
                (Duration)null,
                (Node)null,
                Integer.MIN_VALUE);
    }

    private QuorumStatement(String query, List<Object> positionalValues, Map<CqlIdentifier, Object> namedValues, String executionProfileName, DriverExecutionProfile executionProfile, CqlIdentifier keyspace, CqlIdentifier routingKeyspace, ByteBuffer routingKey, Token routingToken, Map<String, ByteBuffer> customPayload, Boolean idempotent, boolean tracing, long timestamp, ByteBuffer pagingState, int pageSize, ConsistencyLevel consistencyLevel, ConsistencyLevel serialConsistencyLevel, Duration timeout, Node node, int nowInSeconds) {
        if (!positionalValues.isEmpty() && !namedValues.isEmpty()) {
            throw new IllegalArgumentException("Can't have both positional and named values");
        } else {
            this.query = query;
            this.positionalValues = NullAllowingImmutableList.copyOf(positionalValues);
            this.namedValues = NullAllowingImmutableMap.copyOf(namedValues);
            this.executionProfileName = executionProfileName;
            this.executionProfile = executionProfile;
            this.keyspace = keyspace;
            this.routingKeyspace = routingKeyspace;
            this.routingKey = routingKey;
            this.routingToken = routingToken;
            this.customPayload = customPayload;
            this.idempotent = idempotent;
            this.tracing = tracing;
            this.timestamp = timestamp;
            this.pagingState = pagingState;
            this.pageSize = pageSize;
            this.timeout = timeout;
            this.node = node;
            this.nowInSeconds = nowInSeconds;
        }
    }

    @NonNull
    public String getQuery() {
        return this.query;
    }

    @NonNull
    public SimpleStatement setQuery(@NonNull String newQuery) {
        return new QuorumStatement(newQuery, this.positionalValues, this.namedValues, this.executionProfileName, this.executionProfile, this.keyspace, this.routingKeyspace, this.routingKey, this.routingToken, this.customPayload, this.idempotent, this.tracing, this.timestamp, this.pagingState, this.pageSize, this.consistencyLevel, this.serialConsistencyLevel, this.timeout, this.node, this.nowInSeconds);
    }

    @NonNull
    public List<Object> getPositionalValues() {
        return this.positionalValues;
    }

    @NonNull
    public SimpleStatement setPositionalValues(@NonNull List<Object> newPositionalValues) {
        return new QuorumStatement(this.query, newPositionalValues, this.namedValues, this.executionProfileName, this.executionProfile, this.keyspace, this.routingKeyspace, this.routingKey, this.routingToken, this.customPayload, this.idempotent, this.tracing, this.timestamp, this.pagingState, this.pageSize, this.consistencyLevel, this.serialConsistencyLevel, this.timeout, this.node, this.nowInSeconds);
    }

    @NonNull
    public Map<CqlIdentifier, Object> getNamedValues() {
        return this.namedValues;
    }

    @NonNull
    public SimpleStatement setNamedValuesWithIds(@NonNull Map<CqlIdentifier, Object> newNamedValues) {
        return new QuorumStatement(this.query, this.positionalValues, newNamedValues, this.executionProfileName, this.executionProfile, this.keyspace, this.routingKeyspace, this.routingKey, this.routingToken, this.customPayload, this.idempotent, this.tracing, this.timestamp, this.pagingState, this.pageSize, this.consistencyLevel, this.serialConsistencyLevel, this.timeout, this.node, this.nowInSeconds);
    }

    @Nullable
    public String getExecutionProfileName() {
        return this.executionProfileName;
    }

    @NonNull
    public SimpleStatement setExecutionProfileName(@Nullable String newConfigProfileName) {
        return new QuorumStatement(this.query, this.positionalValues, this.namedValues, newConfigProfileName, newConfigProfileName == null ? this.executionProfile : null, this.keyspace, this.routingKeyspace, this.routingKey, this.routingToken, this.customPayload, this.idempotent, this.tracing, this.timestamp, this.pagingState, this.pageSize, this.consistencyLevel, this.serialConsistencyLevel, this.timeout, this.node, this.nowInSeconds);
    }

    @Nullable
    public DriverExecutionProfile getExecutionProfile() {
        return this.executionProfile;
    }

    @NonNull
    public SimpleStatement setExecutionProfile(@Nullable DriverExecutionProfile newProfile) {
        return new QuorumStatement(this.query, this.positionalValues, this.namedValues, newProfile == null ? this.executionProfileName : null, newProfile, this.keyspace, this.routingKeyspace, this.routingKey, this.routingToken, this.customPayload, this.idempotent, this.tracing, this.timestamp, this.pagingState, this.pageSize, this.consistencyLevel, this.serialConsistencyLevel, this.timeout, this.node, this.nowInSeconds);
    }

    @Nullable
    public CqlIdentifier getKeyspace() {
        return this.keyspace;
    }

    @NonNull
    public SimpleStatement setKeyspace(@Nullable CqlIdentifier newKeyspace) {
        return new QuorumStatement(this.query, this.positionalValues, this.namedValues, this.executionProfileName, this.executionProfile, newKeyspace, this.routingKeyspace, this.routingKey, this.routingToken, this.customPayload, this.idempotent, this.tracing, this.timestamp, this.pagingState, this.pageSize, this.consistencyLevel, this.serialConsistencyLevel, this.timeout, this.node, this.nowInSeconds);
    }

    @Nullable
    public CqlIdentifier getRoutingKeyspace() {
        return this.routingKeyspace;
    }

    @NonNull
    public SimpleStatement setRoutingKeyspace(@Nullable CqlIdentifier newRoutingKeyspace) {
        return new QuorumStatement(this.query, this.positionalValues, this.namedValues, this.executionProfileName, this.executionProfile, this.keyspace, newRoutingKeyspace, this.routingKey, this.routingToken, this.customPayload, this.idempotent, this.tracing, this.timestamp, this.pagingState, this.pageSize, this.consistencyLevel, this.serialConsistencyLevel, this.timeout, this.node, this.nowInSeconds);
    }

    @NonNull
    public SimpleStatement setNode(@Nullable Node newNode) {
        return new QuorumStatement(this.query, this.positionalValues, this.namedValues, this.executionProfileName, this.executionProfile, this.keyspace, this.routingKeyspace, this.routingKey, this.routingToken, this.customPayload, this.idempotent, this.tracing, this.timestamp, this.pagingState, this.pageSize, this.consistencyLevel, this.serialConsistencyLevel, this.timeout, newNode, this.nowInSeconds);
    }

    @Nullable
    public Node getNode() {
        return this.node;
    }

    @Nullable
    public ByteBuffer getRoutingKey() {
        return this.routingKey;
    }

    @NonNull
    public SimpleStatement setRoutingKey(@Nullable ByteBuffer newRoutingKey) {
        return new QuorumStatement(this.query, this.positionalValues, this.namedValues, this.executionProfileName, this.executionProfile, this.keyspace, this.routingKeyspace, newRoutingKey, this.routingToken, this.customPayload, this.idempotent, this.tracing, this.timestamp, this.pagingState, this.pageSize, this.consistencyLevel, this.serialConsistencyLevel, this.timeout, this.node, this.nowInSeconds);
    }

    @Nullable
    public Token getRoutingToken() {
        return this.routingToken;
    }

    @NonNull
    public SimpleStatement setRoutingToken(@Nullable Token newRoutingToken) {
        return new QuorumStatement(this.query, this.positionalValues, this.namedValues, this.executionProfileName, this.executionProfile, this.keyspace, this.routingKeyspace, this.routingKey, newRoutingToken, this.customPayload, this.idempotent, this.tracing, this.timestamp, this.pagingState, this.pageSize, this.consistencyLevel, this.serialConsistencyLevel, this.timeout, this.node, this.nowInSeconds);
    }

    @NonNull
    public Map<String, ByteBuffer> getCustomPayload() {
        return this.customPayload;
    }

    @NonNull
    public SimpleStatement setCustomPayload(@NonNull Map<String, ByteBuffer> newCustomPayload) {
        return new QuorumStatement(this.query, this.positionalValues, this.namedValues, this.executionProfileName, this.executionProfile, this.keyspace, this.routingKeyspace, this.routingKey, this.routingToken, newCustomPayload, this.idempotent, this.tracing, this.timestamp, this.pagingState, this.pageSize, this.consistencyLevel, this.serialConsistencyLevel, this.timeout, this.node, this.nowInSeconds);
    }

    @Nullable
    public Boolean isIdempotent() {
        return this.idempotent;
    }

    @NonNull
    public SimpleStatement setIdempotent(@Nullable Boolean newIdempotence) {
        return new QuorumStatement(this.query, this.positionalValues, this.namedValues, this.executionProfileName, this.executionProfile, this.keyspace, this.routingKeyspace, this.routingKey, this.routingToken, this.customPayload, newIdempotence, this.tracing, this.timestamp, this.pagingState, this.pageSize, this.consistencyLevel, this.serialConsistencyLevel, this.timeout, this.node, this.nowInSeconds);
    }

    public boolean isTracing() {
        return this.tracing;
    }

    @NonNull
    public SimpleStatement setTracing(boolean newTracing) {
        return new QuorumStatement(this.query, this.positionalValues, this.namedValues, this.executionProfileName, this.executionProfile, this.keyspace, this.routingKeyspace, this.routingKey, this.routingToken, this.customPayload, this.idempotent, newTracing, this.timestamp, this.pagingState, this.pageSize, this.consistencyLevel, this.serialConsistencyLevel, this.timeout, this.node, this.nowInSeconds);
    }

    public long getQueryTimestamp() {
        return this.timestamp;
    }

    @NonNull
    public SimpleStatement setQueryTimestamp(long newTimestamp) {
        return new QuorumStatement(this.query, this.positionalValues, this.namedValues, this.executionProfileName, this.executionProfile, this.keyspace, this.routingKeyspace, this.routingKey, this.routingToken, this.customPayload, this.idempotent, this.tracing, newTimestamp, this.pagingState, this.pageSize, this.consistencyLevel, this.serialConsistencyLevel, this.timeout, this.node, this.nowInSeconds);
    }

    @Nullable
    public Duration getTimeout() {
        return this.timeout;
    }

    @NonNull
    public SimpleStatement setTimeout(@Nullable Duration newTimeout) {
        return new QuorumStatement(this.query, this.positionalValues, this.namedValues, this.executionProfileName, this.executionProfile, this.keyspace, this.routingKeyspace, this.routingKey, this.routingToken, this.customPayload, this.idempotent, this.tracing, this.timestamp, this.pagingState, this.pageSize, this.consistencyLevel, this.serialConsistencyLevel, newTimeout, this.node, this.nowInSeconds);
    }

    @Nullable
    public ByteBuffer getPagingState() {
        return this.pagingState;
    }

    @NonNull
    public SimpleStatement setPagingState(@Nullable ByteBuffer newPagingState) {
        return new QuorumStatement(this.query, this.positionalValues, this.namedValues, this.executionProfileName, this.executionProfile, this.keyspace, this.routingKeyspace, this.routingKey, this.routingToken, this.customPayload, this.idempotent, this.tracing, this.timestamp, newPagingState, this.pageSize, this.consistencyLevel, this.serialConsistencyLevel, this.timeout, this.node, this.nowInSeconds);
    }

    public int getPageSize() {
        return this.pageSize;
    }

    @NonNull
    public SimpleStatement setPageSize(int newPageSize) {
        return new QuorumStatement(this.query, this.positionalValues, this.namedValues, this.executionProfileName, this.executionProfile, this.keyspace, this.routingKeyspace, this.routingKey, this.routingToken, this.customPayload, this.idempotent, this.tracing, this.timestamp, this.pagingState, newPageSize, this.consistencyLevel, this.serialConsistencyLevel, this.timeout, this.node, this.nowInSeconds);
    }

    @Nullable
    public ConsistencyLevel getConsistencyLevel() {
        return this.consistencyLevel;
    }

    @NonNull
    public SimpleStatement setConsistencyLevel(@Nullable ConsistencyLevel newConsistencyLevel) {
        return new QuorumStatement(this.query, this.positionalValues, this.namedValues, this.executionProfileName, this.executionProfile, this.keyspace, this.routingKeyspace, this.routingKey, this.routingToken, this.customPayload, this.idempotent, this.tracing, this.timestamp, this.pagingState, this.pageSize, newConsistencyLevel, this.serialConsistencyLevel, this.timeout, this.node, this.nowInSeconds);
    }

    @Nullable
    public ConsistencyLevel getSerialConsistencyLevel() {
        return this.serialConsistencyLevel;
    }

    @NonNull
    public SimpleStatement setSerialConsistencyLevel(@Nullable ConsistencyLevel newSerialConsistencyLevel) {
        return new QuorumStatement(this.query, this.positionalValues, this.namedValues, this.executionProfileName, this.executionProfile, this.keyspace, this.routingKeyspace, this.routingKey, this.routingToken, this.customPayload, this.idempotent, this.tracing, this.timestamp, this.pagingState, this.pageSize, this.consistencyLevel, newSerialConsistencyLevel, this.timeout, this.node, this.nowInSeconds);
    }

    public int getNowInSeconds() {
        return this.nowInSeconds;
    }

    @NonNull
    public SimpleStatement setNowInSeconds(int newNowInSeconds) {
        return new QuorumStatement(this.query, this.positionalValues, this.namedValues, this.executionProfileName, this.executionProfile, this.keyspace, this.routingKeyspace, this.routingKey, this.routingToken, this.customPayload, this.idempotent, this.tracing, this.timestamp, this.pagingState, this.pageSize, this.consistencyLevel, this.serialConsistencyLevel, this.timeout, this.node, newNowInSeconds);
    }

    public static Map<CqlIdentifier, Object> wrapKeys(Map<String, Object> namedValues) {
        NullAllowingImmutableMap.Builder<CqlIdentifier, Object> builder = NullAllowingImmutableMap.builder();
        Iterator<Map.Entry<String, Object>> var2 = namedValues.entrySet().iterator();

        while(var2.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry)var2.next();
            builder.put(CqlIdentifier.fromCql((String)entry.getKey()), entry.getValue());
        }

        return builder.build();
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof QuorumStatement)) {
            return false;
        } else {
            QuorumStatement that = (QuorumStatement)other;
            return this.query.equals(that.query) && this.positionalValues.equals(that.positionalValues) && this.namedValues.equals(that.namedValues) && Objects.equals(this.executionProfileName, that.executionProfileName) && Objects.equals(this.executionProfile, that.executionProfile) && Objects.equals(this.keyspace, that.keyspace) && Objects.equals(this.routingKeyspace, that.routingKeyspace) && Objects.equals(this.routingKey, that.routingKey) && Objects.equals(this.routingToken, that.routingToken) && Objects.equals(this.customPayload, that.customPayload) && Objects.equals(this.idempotent, that.idempotent) && this.tracing == that.tracing && this.timestamp == that.timestamp && Objects.equals(this.pagingState, that.pagingState) && this.pageSize == that.pageSize && Objects.equals(this.consistencyLevel, that.consistencyLevel) && Objects.equals(this.serialConsistencyLevel, that.serialConsistencyLevel) && Objects.equals(this.timeout, that.timeout) && Objects.equals(this.node, that.node) && this.nowInSeconds == that.nowInSeconds;
        }
    }

    public int hashCode() {
        return Objects.hash(this.query, this.positionalValues, this.namedValues, this.executionProfileName, this.executionProfile, this.keyspace, this.routingKeyspace, this.routingKey, this.routingToken, this.customPayload, this.idempotent, this.tracing, this.timestamp, this.pagingState, this.pageSize, this.consistencyLevel, this.serialConsistencyLevel, this.timeout, this.node, this.nowInSeconds);
    }
}
