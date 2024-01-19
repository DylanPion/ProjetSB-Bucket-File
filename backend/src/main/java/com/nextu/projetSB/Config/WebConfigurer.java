package com.nextu.projetSB.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*              Configuration Java pour la négotiation de contenu dans les réponses HTTP du serveur.
La négotiation de contenu est le processssus par lequel le serveur détermine le type de contenu (JSON, XML etc..) à renvoyer en
réponse à une requête client.

Interface WebMvcConfigurer : En implémentant cette interface, la classe peut personnaliser la configuration de Spring MVC,
qui est le module Web de Spring.
 */

@Configuration
public class WebConfigurer implements WebMvcConfigurer {
    @Override
    public void configureContentNegotiation(final ContentNegotiationConfigurer configurer) {
        configurer.favorParameter(false)
                .parameterName("mediaType")
                .ignoreAcceptHeader(false)
                .useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML)
                .mediaType("json", MediaType.APPLICATION_JSON);
    }
}






