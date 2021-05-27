package me.sadensmol.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class CountryInfo (
    @JsonProperty("cioc") val id:String,
    val borders:Array<String>
    )