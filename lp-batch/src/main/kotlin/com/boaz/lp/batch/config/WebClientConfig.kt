package com.boaz.lp.batch.config

import io.netty.channel.ChannelOption
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.time.Duration

@Configuration
class WebClientConfig(
    private val riotApiProperties: RiotApiProperties
) {
    private val log = org.slf4j.LoggerFactory.getLogger(javaClass)

    @Bean
    fun riotApiWebClient(): WebClient {
        log.info("Riot API key loaded: ${if (riotApiProperties.key.isNotBlank()) "${riotApiProperties.key.take(10)}..." else "EMPTY"}")

        val httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5_000)
            .responseTimeout(Duration.ofSeconds(10))

        return WebClient.builder()
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .defaultHeader("X-Riot-Token", riotApiProperties.key)
            .codecs { it.defaultCodecs().maxInMemorySize(2 * 1024 * 1024) }
            .build()
    }
}
