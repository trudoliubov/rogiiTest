package com.rogii.core.config.api

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.ComponentScans
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScans(
    ComponentScan(value = ["com.rogii.api"])
)
class ClientApiConfig {

    @Bean(destroyMethod = "close")
    @RogiiApiClient
    fun rogiiApiClient() = HttpClient() {
        install(ContentNegotiation) {
            jackson {
                setSerializationInclusion(JsonInclude.Include.NON_NULL)
                registerModule(JavaTimeModule())
            }
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 20000
        }
        expectSuccess = false
        defaultRequest {
            contentType(ContentType.Application.Json)
            url("https://reqres.in")
        }
    }
}

@Qualifier("rogiiApiClient")
annotation class RogiiApiClient