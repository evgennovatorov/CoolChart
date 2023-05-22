package com.evgenii.coolgraph.ui.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evgenii.coolgraph.business.GetPointUseCase
import com.evgenii.coolgraph.common.AppLogger
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

private val TAG = "StartViewModel"

class StartViewModel(
    private val getPointUseCaseImpl: GetPointUseCase,
    private val logger: AppLogger
): ViewModel() {

    private val resultInternal = MutableSharedFlow<PointsLoadingState>()
    val result = resultInternal.asSharedFlow()

    val isLoading = getPointUseCaseImpl.isRunning

    private val errorHandler = CoroutineExceptionHandler { context, throwable ->
        logger.error(TAG, "error handler", throwable)
        viewModelScope.launch {
            resultInternal.emit(ErrorState)
        }
    }

    fun loadPoints(count: String) {
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            count.safeGetInt()?.let {
                val points = getPointUseCaseImpl(it)
                resultInternal.emit(SuccessState(points))
            }
        }
    }

    private fun String.safeGetInt(): Int? =
        try {
            toInt()
        } catch (e: Exception) {
            resultInternal.tryEmit(ErrorState)
            null
        }
}