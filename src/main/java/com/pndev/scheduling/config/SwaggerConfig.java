package com.pndev.scheduling.config;

                        import io.swagger.v3.oas.models.OpenAPI;
                        import io.swagger.v3.oas.models.info.Info;
                        import io.swagger.v3.oas.models.info.Contact;
                        import io.swagger.v3.oas.models.security.SecurityRequirement;
                        import io.swagger.v3.oas.models.security.SecurityScheme;
                        import org.springframework.context.annotation.Bean;
                        import org.springframework.context.annotation.Configuration;

                        @Configuration
                        public class SwaggerConfig {

                            @Bean
                            public OpenAPI customOpenAPI() {
                                final String securitySchemeName = "bearerAuth";
                                return new OpenAPI()
                                        .info(new Info()
                                                .title("API de Clínica Médica")
                                                .description("API para gerenciamento de clínica médica")
                                                .version("1.0.0")
                                                .contact(new Contact()
                                                        .name("Clínica Application")
                                                        .email("contato@clinica.com")))
                                        .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                                        .components(new io.swagger.v3.oas.models.Components()
                                                .addSecuritySchemes(securitySchemeName,
                                                        new SecurityScheme()
                                                                .name(securitySchemeName)
                                                                .type(SecurityScheme.Type.HTTP)
                                                                .scheme("bearer")
                                                                .bearerFormat("JWT")));
                            }
                        }