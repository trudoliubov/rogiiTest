package com.rogii.api.clients.data

import com.fasterxml.jackson.annotation.JsonProperty

data class RegisterRequest(
    @JsonProperty("email")      var email: String,
    @JsonProperty("password")   var password: String?
)