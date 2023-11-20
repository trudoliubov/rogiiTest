package com.rogii.api.clients

import com.rogii.api.clients.data.RegisterRequest
import com.rogii.api.clients.data.UpdateUserRequest
import com.rogii.core.config.api.RogiiApiClient
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import org.springframework.stereotype.Component

@Component
class ReqresApi(
    @RogiiApiClient
    private val client: HttpClient
) {

    suspend fun getUsers(
        page: Int? = null,
        perPage: Int? = null
    ): HttpResponse =
        client.get("/api/users") {
            page?.let { parameter("page", page) }
            perPage?.let { parameter("per_page", perPage) }
        }

    suspend fun getUser(
        id: Int
    ): HttpResponse =
        client.get("/api/users/$id")

    suspend fun getResources(
        resource: String,
        page: Int? = null,
        perPage: Int? = null
    ): HttpResponse =
        client.get("/api/$resource") {
            page?.let { parameter("page", page) }
            perPage?.let { parameter("per_page", perPage) }
        }

    suspend fun getResource(
        resource: String,
        id: Int
    ): HttpResponse =
        client.get("/api/$resource/$id")

    suspend fun postRegister(
        email: String,
        password: String?
    ): HttpResponse =
        client.post("/api/register") {
            setBody(RegisterRequest(email, password))
        }

    suspend fun patchUpdateUser(
        id: Int,
        name: String? = null,
        job: String? = null
    ): HttpResponse =
        client.patch("/api/users/$id") {
            val updateUserRequest = UpdateUserRequest(name, job)
            val body = mapOf(
                "name" to updateUserRequest.name,
                "job" to updateUserRequest.job
            ).filterValues { it != null }
            setBody(body)
        }

    suspend fun putUpdateUser(
        id: Int,
        name: String,
        job: String
    ): HttpResponse =
        client.put("/api/users/$id") {
            setBody(UpdateUserRequest(name, job))
        }

    suspend fun deleteUser(
        id: Int
    ): HttpResponse =
        client.delete("/api/users/$id")
}