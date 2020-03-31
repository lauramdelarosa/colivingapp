package com.naez.colivingapp.data.server.endpoints

import com.naez.colivingapp.data.server.response.SpaceResult
import retrofit2.Response
import retrofit2.http.GET

interface SpaceService {
    @GET("coliving")
    suspend fun listColivingAsync(): Response<SpaceResult>
}