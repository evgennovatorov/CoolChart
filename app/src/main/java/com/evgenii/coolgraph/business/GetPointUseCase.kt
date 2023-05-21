package com.evgenii.coolgraph.business

import com.evgenii.coolgraph.business.model.Point
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface GetPointUseCase {

    val isRunning: SharedFlow<Boolean>

    suspend operator fun invoke(count: Int): List<Point>
}