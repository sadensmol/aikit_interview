package me.sadensmol.service

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import me.sadensmol.model.CountriesPath
import me.sadensmol.model.CountryInfo
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitExchange

@Service
class CountriesPathService(private val countriesListWebClient:WebClient) {

    private val dataLoadingMutex = Mutex()

    private var data:Array<CountryInfo>? = null

    private suspend fun fetchData () {
        data = countriesListWebClient.get().awaitExchange { it.awaitBody() }
    }

    suspend fun getAllRoutesFromTo(origin: String, destination: String): CountriesPath {
        if (data == null) dataLoadingMutex.withLock { if (data == null) fetchData()}
        return CountriesPath(arrayOf("AAA","BBB"))
    }
}