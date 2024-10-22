package com.pratham.movieapppoc.common

import com.movieapppoc.common.kotlin.coroutine.api.DispatcherProvider
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class TestDispatcherProvider : DispatcherProvider {
    override val main: CoroutineContext = Dispatchers.Unconfined
    override val io: CoroutineContext = Dispatchers.Unconfined
    override val default: CoroutineContext = Dispatchers.Unconfined
    override val unconfined: CoroutineContext = Dispatchers.Unconfined
    override val limitedParallelism: CoroutineContext = Dispatchers.Unconfined
}