package me.sadensmol.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.util.MimeTypeUtils
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class ServiceConfiguration {

    @Bean
    fun objectMapper(): ObjectMapper =
        ObjectMapper()
            .registerModule(KotlinModule())
            .registerModule(JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .enable(MapperFeature.REQUIRE_SETTERS_FOR_GETTERS)
            .enable(MapperFeature.PROPAGATE_TRANSIENT_MARKER)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Bean
    fun countriesListWebClient(@Value("\${aikit.contries-list.url}") countriesListUrl: String, objectMapper: ObjectMapper): WebClient {
        var strategies: ExchangeStrategies = ExchangeStrategies
            .builder()
            .codecs { clientDefaultCodecsConfigurer ->
                clientDefaultCodecsConfigurer.defaultCodecs()
                    .jackson2JsonDecoder(Jackson2JsonDecoder(objectMapper, MimeTypeUtils.parseMimeType(MediaType.TEXT_PLAIN_VALUE)))
                clientDefaultCodecsConfigurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024)
            }.build()

        return WebClient
            .builder()
            .exchangeStrategies(strategies).baseUrl(countriesListUrl)
            .build()
    }
}