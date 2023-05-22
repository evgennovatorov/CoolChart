package com.evgenii.coolgraph.ui.start

import com.evgenii.coolgraph.business.model.Point

sealed class PointsLoadingState

class SuccessState(val points: List<Point>): PointsLoadingState()

object ErrorState : PointsLoadingState()