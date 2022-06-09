package org.brienze.crypto.data.analysis.config

import com.google.gson.Gson
import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.time.Duration
import java.util.concurrent.TimeUnit


@Configuration
class BeanConfig {

    @Bean
    fun mapper(): Gson {
        return Gson()
    }

    @Bean
    fun webClient(): WebClient.Builder {
        val httpClient: HttpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .responseTimeout(Duration.ofMillis(5000))
            .doOnConnected { conn ->
                conn.addHandlerLast(ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                    .addHandlerLast(WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS))
            }

        return WebClient
            .builder()
            .clientConnector(ReactorClientHttpConnector(httpClient))
    }

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplateBuilder().build()
    }

}