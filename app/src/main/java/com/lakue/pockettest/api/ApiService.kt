package com.lakue.pockettest.api

import com.lakue.pockettest.model.ResponsePocket
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/api/v2/pokemon")
    suspend fun getPocketInfo(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<ResponsePocket>
}