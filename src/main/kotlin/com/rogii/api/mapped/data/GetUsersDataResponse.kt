package com.rogii.api.mapped.data

import com.fasterxml.jackson.annotation.JsonProperty

data class GetUsersDataResponse(
    @JsonProperty("page")           val page: Int,
    @JsonProperty("per_page")       val perPage: Int,
    @JsonProperty("total")          val total: Int,
    @JsonProperty("total_pages")    val totalPages: Int,
    @JsonProperty("data")           val data: List<UsersData>,
    @JsonProperty("support")        val support: UsersSupport
)

data class UsersData(
    @JsonProperty("id")             val id: Int,
    @JsonProperty("email")          val email: String,
    @JsonProperty("first_name")     val firstName: String,
    @JsonProperty("last_name")      val lastName: String,
    @JsonProperty("avatar")         val avatar: String
)

data class UsersSupport(
    @JsonProperty("url")            val url: String,
    @JsonProperty("text")           val text: String,
)