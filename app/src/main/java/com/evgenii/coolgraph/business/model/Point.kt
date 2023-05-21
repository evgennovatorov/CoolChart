package com.evgenii.coolgraph.business.model

import com.evgenii.coolgraph.domain.PointResponse

data class Point(
    val x: Double,
    val y: Double
) {
    companion object {
        fun map(response: PointResponse) = Point(
            response.x,
            response.y
        )
    }
}