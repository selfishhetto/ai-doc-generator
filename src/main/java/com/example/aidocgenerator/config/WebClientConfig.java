package com.example.aidocgenerator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(AiProperties aiProperties) {
        return WebClient.builder()
                .baseUrl(aiProperties.getBaseUrl())
                .defaultHeader("x-goog-api-key", aiProperties.getApiKey())
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}
