package me.sadensmol.controller

import io.mockk.clearAllMocks
import io.mockk.confirmVerified
import io.mockk.mockk
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.web.reactive.server.WebTestClient

@WebFluxTest(controllers = [Erc20Controller::class])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Erc20ControllerTest(
    @Autowired private val webTestClient: WebTestClient,
    @Autowired private val erc20Service: Erc20Service,
    @Value("\${contract.address}") private val contractAddress: String,
    @Value("\${holder.address}") private val holderAddress: String,
) {

    @TestConfiguration
    class TestConfig {
        @Bean
        fun erc20Service() = mockk<Erc20Service>()
    }

    @BeforeEach
    fun setUpEach() {
        clearAllMocks()
    }

    @AfterEach
    fun tearDownEach() {
        confirmVerified(erc20Service)
    }

    @Test
    fun `when looking route from CZE to ITA then return CZE,AUT,ITA`() {
        webTestClient.get()
            .uri("/{contract}/{holder}", contractAddress, holderAddress)
            .exchange()
            .expectStatus().isOk
    }
}