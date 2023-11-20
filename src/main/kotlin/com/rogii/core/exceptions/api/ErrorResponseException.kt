package com.rogii.core.exceptions.api

import com.rogii.core.extentions.kotlin.unescapeUnicode
import io.ktor.http.*

class ErrorResponseException : AssertionError {
    constructor(
        requestMethod: HttpMethod,
        requestUrl: String,
        status: HttpStatusCode,
        responseBody: String,
        xRequestId: String? = null,
        error: String? = null,
        message: String =
            """|
            |   Запрос завершился с ошибкой!
            |   ===Request===
            |   Method: ${requestMethod.value}
            |   Url: $requestUrl
            |   ===Response===
            |   Status: $status
            |   x_request_id: ${xRequestId ?: "Unknown"}
            |   Error: ${error ?: "Unknown"}
            |   Body: ${responseBody.unescapeUnicode()}
            """.trimMargin()
    ): super(message)
}