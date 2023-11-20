package com.rogii.api.services

import com.rogii.api.mapped.UsersMappedApi
import com.rogii.core.annotation.api.RogiiApiService
import com.rogii.core.data.objects.ResourceData
import com.rogii.core.data.objects.UpdateUserData
import com.rogii.core.data.objects.UserData
import com.rogii.core.extentions.kotlin.step
import io.ktor.client.statement.*

@RogiiApiService
class UsersServiceApi(
    private val usersMappedApi: UsersMappedApi
) {

    /**
     * Метод получения пользователей с пагинацией
     * @param page[Int] страница
     * @param perPage[Int]
     * @return List[UserData] объект
     */
    fun getUsers(
        page: Int? = null,
        perPage: Int? = null
    ): List<UserData> =
        step("Получаю данные пользователей") {
            val responseEntity = usersMappedApi.getUsers(page, perPage)
            it.parameter("response", responseEntity)
            val responseData = responseEntity.data.map { user ->
                UserData(
                    id = user.id,
                    email = user.email,
                    firstName = user.firstName,
                    lastName = user.lastName,
                    avatar = user.avatar
                )
            }
            it.parameter("responseData", responseData)
        }

    /**
     * Метод получения пользователя по id
     * @param id[Int] идентификатор пользователя
     * @return [UserData] объект пользователя
     */
    fun getUser(
        id: Int
    ): UserData =
        step("Получаю пользователя по id", "id" to id) {
            val responseEntity = usersMappedApi.getUser(id)
            it.parameter("responseEntity", responseEntity)
            val responseData = responseEntity.data
            UserData(
                id = responseData.id,
                email = responseData.email,
                firstName = responseData.firstName,
                lastName = responseData.lastName,
                avatar = responseData.avatar
            )
        }

    fun updateUser(
        id: Int,
        name: String? = null,
        job: String? = null
    ): UpdateUserData =
        step("Обновляем данные пользователя по id", "id" to id) {
            val responseEntity = usersMappedApi.updateUser(id, name, job)
            it.parameter("responseEntity", responseEntity)
            UpdateUserData(
                name = responseEntity.name ?: "DefaultName",
                job = responseEntity.job ?: "DefaultJob",
                updateAt = responseEntity.updatedAt
            )
        }

    fun deleteUser(
        id: Int
    ): HttpResponse =
        step("Удаляем пользователя по id") {
            val response = usersMappedApi.deleteUser(id)
            it.parameter("response", response)
        }
}