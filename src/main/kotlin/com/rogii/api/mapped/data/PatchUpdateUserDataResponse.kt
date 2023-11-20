package com.rogii.api.mapped.data

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

data class UpdateUserDataResponse(
    @JsonProperty("name")           val name: String?,
    @JsonProperty("job")            val job: String?,
    @JsonProperty("updatedAt")      val updatedAt: Instant
)