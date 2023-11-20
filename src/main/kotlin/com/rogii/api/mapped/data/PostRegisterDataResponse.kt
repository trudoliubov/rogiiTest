package com.rogii.api.mapped.data

import com.fasterxml.jackson.annotation.JsonProperty

data class RegisterDataResponse(
    @JsonProperty("id")               val id: Int,
    @JsonProperty("token")            val token: String
)