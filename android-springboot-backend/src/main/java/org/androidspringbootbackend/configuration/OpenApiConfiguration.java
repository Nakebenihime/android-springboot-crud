package org.androidspringbootbackend.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Value("${api.info.title}")
    private String title;

    @Value("${api.info.description}")
    private String description;

    @Value("${api.info.version}")
    private String version;

    @Value("${api.contact.email}")
    private String contact;

    @Value("${api.license.name}")
    private String name;

    @Value("${api.license.url}")
    private String url;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(
                        new io.swagger.v3.oas.models.info.Info()
                                .title(title)
                                .version(version)
                                .description(description)
                                .contact(
                                        new Contact()
                                                .email(contact)
                                )
                                .license(
                                        new License()
                                                .name(name)
                                                .url(url)
                                )
                );
    }
}