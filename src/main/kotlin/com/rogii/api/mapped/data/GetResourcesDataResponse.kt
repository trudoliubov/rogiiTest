package com.rogii.api.mapped.data

import com.fasterxml.jackson.annotation.JsonProperty

data class GetResourcesDataResponse(
    @JsonProperty("page")               val page: Int,
    @JsonProperty("per_page")           val perPage: Int,
    @JsonProperty("total")              val total: Int,
    @JsonProperty("total_pages")        val totalPages: Int,
    @JsonProperty("data")               val data: List<ResourcesData>,
    @JsonProperty("support")            val support: ResourcesSupport
)

data class ResourcesData(
    @JsonProperty("id")                 val id: Int,
    @JsonProperty("name")               val name: String,
    @JsonProperty("year")               val year: Int,
    @JsonProperty("color")              val color: String,
    @JsonProperty("pantone_value")      val pantoneValue: String
)

data class ResourcesSupport(
    @JsonProperty("url")            val url: String,
    @JsonProperty("text")           val text: String,
)