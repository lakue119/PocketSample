package com.lakue.pockettest.api

import com.lakue.pockettest.model.ResponsePocket
import retrofit2.Response

interface ApiHelper {
    suspend fun getPocketInfo(imit: Int, offset: Int): Response<ResponsePocket>
}