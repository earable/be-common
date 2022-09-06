package ai.earable.platform.common.webflux.security;

import ai.earable.platform.common.data.user.enums.RoleType;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

/**
 * Created by BinhNH on 6/13/2022
 */
@Configuration
@EnableWebFluxSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${earable.auth.whitelist.path:}")
    private String[] AUTH_WHITELIST;

    private final EarableAuthenticationManager reactiveAuthenticationManager;
    private final SecurityContextRepository securityContextRepository;

    private static final String[] SWAGGER_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/private/**", //TODO: NhuNH explains this path
    };

    @Bean
    public SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {
        String[] whiteList = ArrayUtils.addAll(SWAGGER_WHITELIST, AUTH_WHITELIST);
        return http.exceptionHandling()
                .authenticationEntryPoint(
                    (swe, e) -> { //TODO: Wrap to earable errors details
                        swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return swe.getResponse().writeWith(Mono.just(new DefaultDataBufferFactory().wrap("UNAUTHORIZED".getBytes())));
                    })
                .accessDeniedHandler((swe, e) -> {
                    swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                    return swe.getResponse().writeWith(Mono.just(new DefaultDataBufferFactory().wrap("FORBIDDEN".getBytes())));
                })
                .and()
                .cors().disable()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .authenticationManager(reactiveAuthenticationManager)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .pathMatchers(whiteList).permitAll()
                .anyExchange().authenticated()
                .and().build();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = new StringBuilder(RoleType.ROLE_ADMIN.name()).append(" > ").append(RoleType.ROLE_MANAGER.name()).append(" \n ")
                .append(RoleType.ROLE_MANAGER.name()).append(" > ").append(RoleType.ROLE_USER.name()).append(" \n ")
                .append(RoleType.ROLE_USER.name()).append(" > ").append(RoleType.ROLE_CUSTOMER.name()).toString();
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy());
        return expressionHandler;
    }

}
