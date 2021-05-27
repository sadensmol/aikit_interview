package me.sadensmol.service

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import me.sadensmol.model.CountriesRoute
import me.sadensmol.model.CountryInfo
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitExchange
import java.util.*

@Service
class CountriesRouteService(private val countriesListWebClient: WebClient) {

    private val dataLoadingMutex = Mutex()

    private var data: Array<CountryInfo>? = null

    suspend fun setData(vararg data: CountryInfo) {
        this.data = arrayOf(*data)
    }

    private suspend fun fetchData() {
        data = countriesListWebClient.get().awaitExchange { it.awaitBody() }
    }

    private fun findCountry(id: String): CountryInfo? {
        return data?.firstOrNull { it.id == id }
    }

    suspend fun getAllRoutesFromTo(origin: String, destination: String): CountriesRoute {
        if (data == null) dataLoadingMutex.withLock { if (data == null) fetchData() }

        val path = LinkedList<String>()
        path.add(origin)
        findRoute(origin, destination, path)
        return CountriesRoute(path.toTypedArray())
    }

    private fun findRoute(current: String, destination: String, path: LinkedList<String>): Boolean {
        if (current == destination) return true

        var countryInfo = findCountry(current) ?: return false

        for (ci in countryInfo.borders) {
            if (path.contains(ci)) continue else path.add(ci)
            if (findRoute(ci, destination, path)) return true
            else path.removeLast()
        }

        return false
    }
}