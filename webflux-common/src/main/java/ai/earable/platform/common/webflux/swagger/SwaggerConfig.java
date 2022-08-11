package ai.earable.platform.common.webflux.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Hungnv
 * @date 03/06/2022
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI getOpenAPI() {
        return new OpenAPI()
            // Components section defines Security Scheme "authHeader"
            .components(new Components()
                .addSecuritySchemes("authHeader", new SecurityScheme()
                    .type(SecurityScheme.Type.APIKEY)
                    .in(SecurityScheme.In.HEADER)
                    .name("Authorization")))
            // AddSecurityItem section applies created scheme globally
            .addSecurityItem(new SecurityRequirement().addList("authHeader"));
    }
}
