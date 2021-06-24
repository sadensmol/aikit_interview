package me.sadensmol.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class ServiceConfiguration {

    @Bean
    fun webClient(): WebClient =  WebClient.create()

    @Bean
    fun objectMapper(): ObjectMapper =
        ObjectMapper()
            .registerModule(KotlinModule())
            .registerModule(JavaTimeModule())

            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .enable(MapperFeature.REQUIRE_SETTERS_FOR_GETTERS)
            .enable(MapperFeature.REQUIRE_SETTERS_FOR_GETTERS)
            .enable(MapperFeature.PROPAGATE_TRANSIENT_MARKER)

}