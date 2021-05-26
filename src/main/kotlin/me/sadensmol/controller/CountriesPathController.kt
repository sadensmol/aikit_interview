package me.sadensmol.controller

import me.sadensmol.model.CountriesPath
import me.sadensmol.service.CountriesPathService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class CountriesPathController(private val countriesPathService: CountriesPathService) {

    /**
     * Returns all available paths (routes) from [origin] to [destination]
     *
     * @return list of
     */
    @GetMapping("/routing/{origin}/{destination}")
    suspend fun getAllPaths( @PathVariable origin:String, @PathVariable destination:String): CountriesPath {
        //todo validate

        return countriesPathService.getAllRoutesFromTo(origin,destination)
    }
}