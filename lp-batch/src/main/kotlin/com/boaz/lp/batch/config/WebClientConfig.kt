package com.boaz.lp.batch.config

import io.netty.channel.ChannelOption
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.ClientRequest
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.netty.http.client.HttpClient
import java.time.Duration

@Configuration
class WebClientConfig(
    private val apiKeyHolder: ApiKeyHolder
) {
    private val log = org.slf4j.LoggerFactory.getLogger(javaClass)

    @Bean
    fun riotApiWebClient(): WebClient {
        log.info("Riot API WebClient configured with dynamic API key via ApiKeyHolder")

        val httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5_000)
            .responseTimeout(Duration.ofSeconds(10))

        return WebClient.builder()
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .filter(apiKeyFilter())
            .codecs { it.defaultCodecs().maxInMemorySize(2 * 1024 * 1024) }
            .build()
    }

    private fun apiKeyFilter(): ExchangeFilterFunction {
        return ExchangeFilterFunction.ofRequestProcessor { request ->
            val mutated = ClientRequest.from(request)
                .header("X-Riot-Token", apiKeyHolder.getKey())
                .build()
            Mono.just(mutated)
        }
    }
}
