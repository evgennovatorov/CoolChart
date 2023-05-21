package com.evgenii.coolgraph.api.retrofit

import com.evgenii.coolgraph.domain.PointResponse
import com.evgenii.coolgraph.domain.PointsResponse
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRequests {

    @GET("api/test/points")
    suspend fun getPoints(@Query("count") count: Int): PointsResponse
}