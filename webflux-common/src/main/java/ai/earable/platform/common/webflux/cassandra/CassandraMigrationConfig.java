package ai.earable.platform.common.webflux.cassandra;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.datastax.oss.driver.api.core.config.DefaultDriverOption;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import lombok.extern.slf4j.Slf4j;
import org.cognitor.cassandra.migration.spring.CassandraMigrationAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@EnableConfigurationProperties(CassandraProperties.class)
public class CassandraMigrationConfig {

    @Autowired
    private CassandraProperties cassandraProperties;

    @Bean
    @Qualifier(CassandraMigrationAutoConfiguration.CQL_SESSION_BEAN_NAME)
    public CqlSession cassandraMigrationCqlSession() {
        return cqlSessionBuilder(cassandraProperties).build();
    }

    @Bean
    @Primary
    public CqlSession applicationCqlSession() {
        return cqlSessionBuilder(cassandraProperties).build();
    }

    private CqlSessionBuilder cqlSessionBuilder(CassandraProperties cassandraProperties) {
        Collection<InetSocketAddress> contractEndPoints = cassandraProperties.getContactPoints().stream()
                .map(cp -> new InetSocketAddress(cp, cassandraProperties.getPort()))
                .collect(Collectors.toList());

        return new CqlSessionBuilder().withConfigLoader(DriverConfigLoader.programmaticBuilder()
                .withDuration(DefaultDriverOption.METADATA_SCHEMA_REQUEST_TIMEOUT, cassandraProperties.getConnection().getConnectTimeout())
                .withDuration(DefaultDriverOption.CONNECTION_CONNECT_TIMEOUT, cassandraProperties.getConnection().getConnectTimeout())
                .withDuration(DefaultDriverOption.CONNECTION_INIT_QUERY_TIMEOUT, cassandraProperties.getConnection().getInitQueryTimeout())
                .withDuration(DefaultDriverOption.CONTROL_CONNECTION_TIMEOUT, cassandraProperties.getControlconnection().getTimeout())
                .withDuration(DefaultDriverOption.REQUEST_TIMEOUT, cassandraProperties.getRequest().getTimeout())
                .build())
                .addContactPoints(contractEndPoints)
                .withKeyspace(cassandraProperties.getKeyspaceName())
                .withAuthCredentials(cassandraProperties.getUsername(), cassandraProperties.getPassword())
                .withLocalDatacenter(cassandraProperties.getLocalDatacenter());
    }
}
