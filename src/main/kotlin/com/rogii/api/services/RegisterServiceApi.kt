package com.rogii.api.services

import com.rogii.api.mapped.RegisterMappedApi
import com.rogii.core.annotation.api.RogiiApiService
import com.rogii.core.data.objects.RegisterData
import com.rogii.core.extentions.kotlin.step

@RogiiApiService
class RegisterServiceApi(
    private val registerMappedApi: RegisterMappedApi
) {

    fun register(
        email: String,
        password: String? = null
    ): RegisterData =
        step("Регистрация пользователя") {
            val responseEntity = registerMappedApi.register(email,password)
            it.parameter("responseEntity", responseEntity)
            RegisterData(
                id = responseEntity.id,
                token = responseEntity.token
            )
        }
}