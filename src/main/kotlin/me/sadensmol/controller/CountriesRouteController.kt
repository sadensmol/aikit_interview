package me.sadensmol.controller

import me.sadensmol.model.CountriesRoute
import me.sadensmol.service.CountriesRouteService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class CountriesRouteController(private val countriesPathService: CountriesRouteService) {

    /**
     * Returns a route from [origin] to [destination]
     *
     * @return list of country ids
     */
    @GetMapping("/routing/{origin}/{destination}")
    suspend fun getAllPaths( @PathVariable origin:String, @PathVariable destination:String): CountriesRoute {
        //todo validate

        return countriesPathService.getAllRoutesFromTo(origin,destination)
    }
}