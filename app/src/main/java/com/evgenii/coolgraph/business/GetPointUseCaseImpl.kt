package com.evgenii.coolgraph.business

import com.evgenii.coolgraph.api.Repository
import com.evgenii.coolgraph.business.model.Point
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GetPointUseCaseImpl(
    private val repository: Repository
): GetPointUseCase {

    private val isRunningInternal = MutableStateFlow(false)
    override val isRunning = isRunningInternal.asStateFlow()

    override suspend fun invoke(count: Int): List<Point> {
        isRunningInternal.tryEmit(true)
        return try {
            repository.getPoints(count)
        } catch (e: Exception) {
            isRunningInternal.tryEmit(false)
            throw e
        }
    }
}