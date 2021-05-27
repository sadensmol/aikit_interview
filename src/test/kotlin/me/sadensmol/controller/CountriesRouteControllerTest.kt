package me.sadensmol.controller

import io.mockk.*
import io.mockk.impl.annotations.MockK
import me.sadensmol.model.CountriesRoute
import me.sadensmol.service.CountriesRouteService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.web.reactive.server.WebTestClient
import java.lang.IllegalArgumentException

@WebFluxTest(controllers = [CountriesRouteController::class])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CountriesRouteControllerTest(@Autowired private val webTestClient: WebTestClient,
@Autowired private val countriesRouteService: CountriesRouteService) {

    @TestConfiguration
    class TestConfig {
        @Bean
        fun countriesRouteService() = mockk<CountriesRouteService>()
    }

    @BeforeEach
    fun setUpEach() {
        clearAllMocks()
    }

    @AfterEach
    fun tearDownEach() {
        confirmVerified(countriesRouteService)
    }

    @Test
    fun `when looking route from CZE to ITA then return CZE,AUT,ITA`() {
        coEvery { countriesRouteService.getAllRoutesFromTo("CZE", "ITA") } returns CountriesRoute(arrayOf("CZE", "AUT", "ITA"))

        val countriesRoute = webTestClient.get()
            .uri("/routing/CZE/ITA")
            .exchange()
            .expectStatus().isOk
            .expectBody(CountriesRoute::class.java)
            .returnResult()
            .responseBody


        coVerify(exactly = 1) { countriesRouteService.getAllRoutesFromTo("CZE", "ITA") }

        assertArrayEquals(arrayOf("CZE", "AUT", "ITA"),countriesRoute?.route)
    }
}