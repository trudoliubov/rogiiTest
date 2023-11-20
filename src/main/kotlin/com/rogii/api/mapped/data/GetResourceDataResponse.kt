package com.rogii.api.mapped.data

import com.fasterxml.jackson.annotation.JsonProperty

data class GetResourceDataResponse(
    @JsonProperty("data")               val data: ResourceData,
    @JsonProperty("support")            val support: ResourceSupport
)

data class ResourceData(
    @JsonProperty("id")                 val id: Int,
    @JsonProperty("name")               val name: String,
    @JsonProperty("year")               val year: Int,
    @JsonProperty("color")              val color: String,
    @JsonProperty("pantone_value")      val pantoneValue: String
)

data class ResourceSupport(
    @JsonProperty("url")                val url: String,
    @JsonProperty("text")               val text: String,
)