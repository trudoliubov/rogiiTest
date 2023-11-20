package com.rogii.api.mapped

import com.rogii.api.clients.ReqresApi
import com.rogii.api.mapped.data.RegisterDataResponse
import com.rogii.core.config.api.execute
import org.springframework.stereotype.Component

@Component
class RegisterMappedApi(
    private val reqresApi: ReqresApi
) {

    fun register(
        email: String,
        password: String? = null
    ): RegisterDataResponse =
        execute { reqresApi.postRegister(email, password) }
}