package com.evgenii.coolgraph.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

fun <T> createSingleEventFlow() = MutableSharedFlow<T>(
    replay = 0,
    extraBufferCapacity = 1,
    onBufferOverflow = BufferOverflow.DROP_OLDEST
)

fun <T> Flow<T>.collect(
    lifecycleOwner: LifecycleOwner,
    block: (T) -> Unit
) {
    lifecycleOwner.lifecycleScope.launchWhenResumed {
        collect(block)
    }
}