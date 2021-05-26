package me.sadensmol.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class CountryInfo (
    @JsonProperty("cioc")
    private val id:String,
    private val borders:Array<String>
    )