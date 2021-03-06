package ai.earable.platform.common.webflux.security;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

/**
 * Created by BinhNH on 6/13/2022
 */
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Value("${earable.auth.whitelist.path:}")
    private String[] AUTH_WHITELIST;

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
    public SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http, JwtUtils jwtUtils,
                                                       ReactiveAuthenticationManager reactiveAuthenticationManager) {
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
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .pathMatchers(whiteList).permitAll()
                .anyExchange().authenticated()
                .and()
                .addFilterAt(new JwtTokenAuthenticationFilter(jwtUtils), SecurityWebFiltersOrder.HTTP_BASIC)
                .build();
    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager() {
        return new EarableAuthenticationManager();
    }
}
