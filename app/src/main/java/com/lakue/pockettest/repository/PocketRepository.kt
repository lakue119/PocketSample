package com.lakue.pockettest.repository

import com.lakue.pockettest.api.ApiHelper
import javax.inject.Inject

open class PocketRepository  @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun getPocketInfo(limit: Int, offset: Int) =  apiHelper.getPocketInfo(limit, offset)
}