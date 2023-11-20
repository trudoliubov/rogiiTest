package com.rogii.core.config.api

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.rogii.core.exceptions.api.ErrorResponseException
import com.rogii.core.extentions.kotlin.step
import com.rogii.core.extentions.kotlin.unescapeUnicode
import io.ktor.client.call.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.util.*
import io.ktor.utils.io.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking


suspend fun HttpResponse.checkStatus(): HttpResponse {
    if (status.value >= 400) {
        val body = bodyAsText()
        val error = if (body[0] != '<') {
            val tree = jacksonObjectMapper().readTree(body)
            tree.get("error")?.asText() ?: tree.get("message")?.asText()
        } else null
        throw ErrorResponseException(
            request.method,
            request.url.toString(),
            status,
            body,
            headers[HttpHeaders.XRequestId],
            error
        )
    }
    return this
}

/**
 * Функция, которая позволяет замапить (преобразовать в объект) api запрос
 * suspend api запрос выполняется блокированием потока (следующая строчка будет ждать выполнения)
 * после выполнения запроса происходит его проверка на статус 200 и мапинг
 */
inline fun <reified T> execute(crossinline request: suspend () -> HttpResponse): T =
    step("Выполняю запрос по api") {
        runBlocking(Dispatchers.IO) {
            val httpResponse = request()
            it.parameter("requestMethod", httpResponse.request.method)
            it.parameter("requestUrl", httpResponse.request.url)
            it.parameter("requestHeaders", httpResponse.request.headers)
            it.parameter("requestBody", httpResponse.request.content.bodyToString())

            it.parameter("responseStatus", httpResponse.status)
            it.parameter("responseHeaders", httpResponse.headers)
            it.parameter("responseBody", httpResponse.bodyAsText().unescapeUnicode())
            httpResponse.checkStatus().body()
        }
    }

suspend fun OutgoingContent.bodyToString(): String =
    String(
        when(this) {
            is OutgoingContent.ByteArrayContent ->
                when(this) {
                    is FormDataContent -> ByteReadChannel(
                        formData
                            .entries()
                            .flatMap { e -> e.value.map { "${e.key}=$it"} }
                            .toString()
                            .toByteArray()
                    )
                    else -> ByteReadChannel(this.bytes())
                }
            is OutgoingContent.ReadChannelContent -> this.readFrom()
            is OutgoingContent.WriteChannelContent -> {
                val channel = ByteChannel()
                this.writeTo(channel)
                channel
            }
            else -> ByteReadChannel.Empty
        }.toByteArray(),
        Charsets.UTF_8
    )