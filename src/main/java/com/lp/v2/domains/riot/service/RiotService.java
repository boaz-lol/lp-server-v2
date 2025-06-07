package com.lp.v2.domains.riot.service;

import com.lp.v2.domains.riot.controller.dto.RiotAccountDto;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class RiotService {

    private final WebClient riotClient;

    public RiotService(WebClient riotClient) {
        this.riotClient = riotClient;
    }

    public Mono<RiotAccountDto> getAccountByRiotId(String gameName, String tagLine) {
        return riotClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/riot/account/v1/accounts/by-riot-id/{gameName}/{tagLine}")
                        .build(gameName, tagLine)
                )
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, resp ->
                        resp.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(
                                        new IllegalArgumentException("잘못된 요청: " + body)))
                )
                .onStatus(HttpStatusCode::is5xxServerError, resp ->
                        Mono.error(new IllegalStateException("Riot 서버 오류"))
                )
                .bodyToMono(RiotAccountDto.class);
    }
}
