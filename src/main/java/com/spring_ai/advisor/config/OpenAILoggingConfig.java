package com.spring_ai.advisor.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;

@Configuration
public class OpenAILoggingConfig {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);
    private static final Logger log = LoggerFactory.getLogger(OpenAILoggingConfig.class);

    @Bean
    public RestClientCustomizer restClientCustomizer(){
        return restClientBuilder -> restClientBuilder
                .requestInterceptor((request, body, execution) -> {
                    try {
                        String prettyJson = objectMapper.writerWithDefaultPrettyPrinter()
                                .writeValueAsString(objectMapper.readTree(body));
                        log.info("=== OpenAI Request ===\n{}", prettyJson);
                    } catch (Exception e){
                        log.info("Request Body : {}", new String(body, StandardCharsets.UTF_8));
                    }
                    return execution.execute(request, body);
                });
    }
}
