package com.rogii.api.mapped.data

import com.fasterxml.jackson.annotation.JsonProperty

data class GetUserDataResponse(
    @JsonProperty("data")               val data: UserData,
    @JsonProperty("support")            val support: UserSupport
)

data class UserData(
    @JsonProperty("id")                 val id: Int,
    @JsonProperty("email")              val email: String,
    @JsonProperty("first_name")         val firstName: String,
    @JsonProperty("last_name")          val lastName: String,
    @JsonProperty("avatar")             val avatar: String
)

data class UserSupport(
    @JsonProperty("url")            val url: String,
    @JsonProperty("text")           val text: String,
)