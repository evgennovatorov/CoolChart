package com.evgenii.coolgraph.api

import com.evgenii.coolgraph.business.model.Point
import com.evgenii.coolgraph.domain.PointResponse

interface Repository {
    suspend fun getPoints(count: Int): List<Point>
}