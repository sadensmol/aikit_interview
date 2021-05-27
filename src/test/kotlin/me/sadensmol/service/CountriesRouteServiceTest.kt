package me.sadensmol.service

import io.mockk.clearAllMocks
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import me.sadensmol.model.CountryInfo
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.springframework.web.reactive.function.client.WebClient

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CountriesRouteServiceTest {
    private val webClient = mockk<WebClient>()
    private val countriesRouteService = CountriesRouteService(webClient)

    @Test
    fun `when origin country cannot be found then return origin country only`() = runBlocking{
            countriesRouteService.setData()
        assertArrayEquals(arrayOf("CTR1"), countriesRouteService.getAllRoutesFromTo("CTR1", "CTR2")?.route)
    }


    @Test
    fun `when CTR2 is the boarder country for CTR1 and we are looking for CTR1 to CTR2 path then return CTR2`() = runBlocking {
        countriesRouteService.setData(CountryInfo(id = "CTR1", borders = arrayOf("CTR2","CTR3","CTR4")))
        assertArrayEquals(arrayOf("CTR1", "CTR2"), countriesRouteService.getAllRoutesFromTo("CTR1", "CTR2")?.route)
    }

    @Test
    fun `when CTR5 is connected to the origin through the boarder for country for CTR3 and we are looking for CTR1 to CTR5 path then return CTR1,CTR3,CTR5`() = runBlocking {
        countriesRouteService.setData(CountryInfo(id = "CTR1", borders = arrayOf("CTR2","CTR3","CTR4")),CountryInfo(id="CTR3",borders=arrayOf("CTR1","CTR22","CTR33","CTR5")))
        assertArrayEquals(arrayOf("CTR1", "CTR3", "CTR5"), countriesRouteService.getAllRoutesFromTo("CTR1", "CTR5")?.route)
    }


}