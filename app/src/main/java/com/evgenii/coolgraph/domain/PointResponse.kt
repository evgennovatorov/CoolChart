package com.evgenii.coolgraph.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PointResponse(val x: Float, val y: Float): Parcelable