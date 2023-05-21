package com.evgenii.coolgraph.ui.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evgenii.coolgraph.business.GetPointUseCase
import com.evgenii.coolgraph.business.GetPointUseCaseImpl
import com.evgenii.coolgraph.common.AppLogger
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.logging.Logger

private val TAG = "StartViewModel"

class StartViewModel(
    private val getPointUseCaseImpl: GetPointUseCase,
    private val logger: AppLogger
): ViewModel() {

    private val resultInternal = MutableStateFlow(LoadPointsState.INITIAL)
    val result = resultInternal.asStateFlow()

    val isLoading = getPointUseCaseImpl.isRunning

    private val errorHandler = CoroutineExceptionHandler { context, throwable ->
        logger.error(TAG, "error handler", throwable)
        resultInternal.tryEmit(LoadPointsState.ERROR)
    }

    fun loadPoints(count: String) {
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            count.safeGetInt()?.let {
                getPointUseCaseImpl(it)
                resultInternal.tryEmit(LoadPointsState.SUCCESS)
            }
        }
    }

    private fun String.safeGetInt(): Int? =
        try {
            toInt()
        } catch (e: Exception) {
            resultInternal.tryEmit(LoadPointsState.ERROR)
            null
        }
}