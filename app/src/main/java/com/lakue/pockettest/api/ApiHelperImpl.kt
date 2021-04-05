package com.lakue.pockettest.api

import com.lakue.pockettest.model.ResponsePocket
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService) : ApiHelper {

    override suspend fun getPocketInfo(limit: Int, offset: Int): Response<ResponsePocket> =
        apiService.getPocketInfo(limit, offset)

}