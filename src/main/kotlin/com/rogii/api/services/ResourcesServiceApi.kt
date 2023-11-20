package com.rogii.api.services

import com.rogii.api.mapped.ResourcesMappedApi
import com.rogii.core.annotation.api.RogiiApiService
import com.rogii.core.data.objects.ResourceData
import com.rogii.core.extentions.kotlin.step

@RogiiApiService
class ResourcesServiceApi(
    private val resourcesMappedApi: ResourcesMappedApi
) {
    fun getResources(
        resource: String = "unknown",
        page: Int? = null,
        perPage: Int? = null
    ): List<ResourceData> =
        step("Получаю список ресурсов") {
            val responseEntity = resourcesMappedApi.getResources(resource, page, perPage)
            it.parameter("responseEntity", responseEntity)
            val responseData = responseEntity.data.map { resource ->
                ResourceData(
                    id = resource.id,
                    name = resource.name,
                    year = resource.year,
                    color = resource.color,
                    pantoneValue = resource.pantoneValue
                )
            }
            it.parameter("responseData", responseData)
        }

    fun getResource(
        resource: String = "unknown",
        id: Int
    ): ResourceData =
        step("Получаю ресурс по id") {
            val responseEntity = resourcesMappedApi.getResource(resource, id)
            it.parameter("responseEntity", responseEntity)
            val responseData = responseEntity.data
            ResourceData(
                id = responseData.id,
                name = responseData.name,
                year = responseData.year,
                color = responseData.color,
                pantoneValue = responseData.pantoneValue
            )
        }
}