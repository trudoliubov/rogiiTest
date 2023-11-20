package com.rogii.api.clients.data

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateUserRequest(
    @JsonProperty("name")       var name: String?,
    @JsonProperty("job")        var job: String?
)