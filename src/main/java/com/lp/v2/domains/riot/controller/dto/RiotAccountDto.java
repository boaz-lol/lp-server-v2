package com.lp.v2.domains.riot.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RiotAccountDto {

    /** 플레이어의 게임 내 이름 */
    @JsonProperty("gameName")
    private String gameName;

    /** 태그라인(예: “KR” 등) */
    @JsonProperty("tagLine")
    private String tagLine;

    /** Riot 전역 고유 ID (PUUID) */
    private String puuid;

}
