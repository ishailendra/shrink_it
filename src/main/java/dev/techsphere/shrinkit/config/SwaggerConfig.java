package dev.techsphere.shrinkit.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Shrink It")
                        .description("URL shortening service, which provides short aliases for redirection of long URLs.")
                        .version("v0.0.1"));
    }
}
