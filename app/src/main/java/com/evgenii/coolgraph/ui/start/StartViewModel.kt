package com.evgenii.coolgraph.ui.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evgenii.coolgraph.business.GetPointUseCase
import com.evgenii.coolgraph.business.model.Point
import com.evgenii.coolgraph.common.AppLogger
import com.evgenii.coolgraph.common.createSingleEventFlow
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private val TAG = "StartViewModel"

class StartViewModel(
    private val getPointUseCaseImpl: GetPointUseCase,
    private val logger: AppLogger
): ViewModel() {

    private val resultInternal = createSingleEventFlow<List<Point>>()
    val result = resultInternal.asSharedFlow()

    private val errorInternal = MutableStateFlow(false)
    val error = errorInternal.asStateFlow()

    val isLoading = getPointUseCaseImpl.isRunning

    private val errorHandler = CoroutineExceptionHandler { context, throwable ->
        logger.error(TAG, "error handler", throwable)
        errorInternal.tryEmit(true)
    }

    fun loadPoints(count: String) {
        errorInternal.tryEmit(false)
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            count.safeGetInt()?.let {
                val points = getPointUseCaseImpl(it)
                val c = resultInternal.subscriptionCount.value
                resultInternal.emit(points)
            }
        }
    }

    private fun String.safeGetInt(): Int? =
        try {
            toInt()
        } catch (e: Exception) {
            errorInternal.tryEmit(true)
            null
        }
}