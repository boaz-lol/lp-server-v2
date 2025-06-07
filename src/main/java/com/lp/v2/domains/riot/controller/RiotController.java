package com.lp.v2.domains.riot.controller;

import com.lp.v2.domains.riot.controller.dto.RiotAccountDto;
import com.lp.v2.domains.riot.service.RiotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/riot")
public class RiotController {

    private final RiotService riotService;

    /**
     * GET /api/riot/account/{gameName}/{tagLine}
     * ex) /api/riot/account/리콩플러스/KR1
     */
    @GetMapping("/account/{gameName}/{tagLine}")
    public Mono<ResponseEntity<RiotAccountDto>> getByRiotId(
            @PathVariable String gameName,
            @PathVariable String tagLine) {
        if (!StringUtils.hasText(gameName) || !StringUtils.hasText(tagLine)) {
            return Mono.just(ResponseEntity.badRequest().build());
        }

        return riotService.getAccountByRiotId(gameName, tagLine)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}