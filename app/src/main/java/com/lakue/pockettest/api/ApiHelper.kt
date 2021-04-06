package com.lakue.pockettest.api

import com.lakue.pockettest.model.ResponsePocket
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import retrofit2.Response

open interface ApiHelper {
    suspend fun getPocketInfo(imit: Int, offset: Int): Response<ResponsePocket>
}