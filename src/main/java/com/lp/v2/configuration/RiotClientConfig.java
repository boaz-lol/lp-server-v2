package com.lp.v2.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class RiotClientConfig {

    @Value("${riot.api.key}")
    private String apiKey;

    @Value("${riot.api.region}")
    private String region;

    @Value("${riot.api.timeout}")
    private int timeout;

    @Bean
    public WebClient riotWebClient(WebClient.Builder builder) {
        String baseUrl = String.format("https://%s.api.riotgames.com", region);

        return builder
                .baseUrl(baseUrl)
                .defaultHeader("X-Riot-Token", apiKey)
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create()
                                .responseTimeout(Duration.ofMillis(timeout))
                ))
                .build();
    }
}

