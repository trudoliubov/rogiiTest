package com.rogii.api.mapped

import com.rogii.api.clients.ReqresApi
import com.rogii.api.mapped.data.GetUserDataResponse
import com.rogii.api.mapped.data.GetUsersDataResponse
import com.rogii.api.mapped.data.UpdateUserDataResponse
import com.rogii.core.config.api.execute
import io.ktor.client.statement.*
import org.springframework.stereotype.Component

@Component
class UsersMappedApi(
    private val reqresApi: ReqresApi
) {
    fun getUsers(
        page: Int? = null,
        perPage: Int? = null
    ): GetUsersDataResponse =
        execute { reqresApi.getUsers(page, perPage) }

    fun getUser(
        id: Int
    ): GetUserDataResponse =
        execute { reqresApi.getUser(id) }

    fun updateUser(
        id: Int,
        name: String? = null,
        job: String? = null
    ): UpdateUserDataResponse {
        return if (name.isNullOrBlank() || job.isNullOrBlank()) {
            execute<UpdateUserDataResponse> { reqresApi.patchUpdateUser(id, name, job) }
        } else {
            execute<UpdateUserDataResponse> { reqresApi.putUpdateUser(id, name, job) }
        }
    }

    fun deleteUser(
        id: Int
    ): HttpResponse =
        execute { reqresApi.deleteUser(id) }

}