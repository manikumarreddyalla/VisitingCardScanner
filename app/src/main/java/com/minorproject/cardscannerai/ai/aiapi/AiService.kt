package com.minorproject.cardscannerai.ai.aiapi

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

interface AiService {
    // Generic endpoint call: caller provides full URL path (relative to baseUrl)
    @POST
    suspend fun analyze(@Url path: String, @Body body: Map<String, Any>): Response<Map<String, Any>>
}
