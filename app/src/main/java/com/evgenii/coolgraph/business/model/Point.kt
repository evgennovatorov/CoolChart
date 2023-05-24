package com.evgenii.coolgraph.business.model

import android.os.Parcelable
import com.evgenii.coolgraph.domain.PointResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class Point(
    val x: Float,
    val y: Float
): Parcelable {

    fun xString() = String.format("%.2f", x)

    fun yString() = String.format("%.2f", y)

    companion object {
        fun map(response: PointResponse) = Point(
            response.x,
            response.y
        )
    }
}