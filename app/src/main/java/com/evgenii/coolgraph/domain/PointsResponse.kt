package com.evgenii.coolgraph.domain

import kotlinx.serialization.Serializable

@Serializable
class PointsResponse(
    val points: List<PointResponse>
) {
}