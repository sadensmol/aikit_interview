package me.sadensmol.service

import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.reactive.function.client.WebClient
import java.math.BigDecimal
import kotlin.test.assertEquals

@SpringBootTest
@ActiveProfiles("test")
class Erc20ServiceIntegrationTest(@Autowired private val erc20Service:Erc20Service,
                                  @Value("\${contract.address}") private val contractAddress: String,
                                  @Value("\${holder.address}") private val holderAddress: String,) {

    @Test
    fun `when origin country cannot be found then return origin country only`() {
        val result = erc20Service.getTokensAmount(contractAddress,holderAddress)
        assertEquals(BigDecimal("93847846.44574442"),result)
    }

}