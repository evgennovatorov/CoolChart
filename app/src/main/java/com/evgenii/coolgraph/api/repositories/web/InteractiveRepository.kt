package com.evgenii.coolgraph.api.repositories.web

import com.evgenii.coolgraph.api.retrofit.ApiRequests
import com.evgenii.coolgraph.business.model.Point

class InteractiveRepository(
    private val apiRequests: ApiRequests
): Repository {

    override suspend fun getPoints(count: Int): List<Point> {
        return apiRequests.getPoints(count)
            .points
            .sortedBy { it.x }
            .map(Point::map)
    }
}