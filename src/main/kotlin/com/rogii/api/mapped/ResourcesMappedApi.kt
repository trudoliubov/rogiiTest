package com.rogii.api.mapped

import com.rogii.api.clients.ReqresApi
import com.rogii.api.mapped.data.GetResourceDataResponse
import com.rogii.api.mapped.data.GetResourcesDataResponse
import com.rogii.core.config.api.execute
import org.springframework.stereotype.Component

@Component
class ResourcesMappedApi(
    private val reqresApi: ReqresApi
) {
    fun getResources(
        resource: String,
        page: Int? = null,
        perPage: Int? = null
    ): GetResourcesDataResponse =
        execute { reqresApi.getResources(resource, page, perPage) }

    fun getResource(
        resource: String,
        id: Int
    ): GetResourceDataResponse =
        execute { reqresApi.getResource(resource, id) }
}