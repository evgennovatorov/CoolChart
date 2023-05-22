package com.evgenii.coolgraph.business.model

import android.os.Parcelable
import com.evgenii.coolgraph.domain.PointResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class Point(
    val x: Float,
    val y: Float
): Parcelable {
    companion object {
        fun map(response: PointResponse) = Point(
            response.x,
            response.y
        )
    }
}