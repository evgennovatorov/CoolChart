package com.evgenii.coolgraph.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class PointsResponse(
    val points: List<PointResponse>
): Parcelable