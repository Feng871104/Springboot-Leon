package com.leon.springbootleon.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.boot.SpringBootVersion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
public class SwaggerConfig {

    private final ResourceLoader resourceLoader;

    public SwaggerConfig(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Bean
    public OpenAPI openAPI() throws IOException {
        final String basicSchemeName = "basicAuth";
        final String securitySchemeName = "bearerAuth";
        var descriptionSource = resourceLoader.getResource("classpath:docs/swagger-description.md");
        var description = StreamUtils.copyToString(descriptionSource.getInputStream(), StandardCharsets.UTF_8);
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(basicSchemeName))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(basicSchemeName,
                                new SecurityScheme()
                                        .name(basicSchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("basic")
                        )
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                )
                .info(new Info()
                        .title("Leon-Project With Spring Boot %s, Java Version %s".formatted(SpringBootVersion.getVersion(), System.getProperty("java.version")))
                        .description(description)
                        .version("1.0")
                        .contact(new Contact()
                                .name("Leon")
                                .email("")
                                .url("https://github.com/Feng871104/Springboot-Leon")
                        )
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")
                        )
                )
                .servers(List.of(
                        new Server().url("https://leonweb.kitazaki.com.tw/").description("Gay Server"),
                        new Server().url("http://localhost:30678/").description("Local Server"),
                        new Server().url("http://26.161.23.197:30678/").description("Dev Server")
                ))
                ;
    }
}
