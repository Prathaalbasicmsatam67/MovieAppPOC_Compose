package com.pratham.coroutine_common

import com.pratham.coroutine_common.api.DispatcherProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CoroutineDispatcherProvider @Inject constructor() : DispatcherProvider {
    override val main: CoroutineContext get() = Dispatchers.Main
    override val default: CoroutineContext get() = Dispatchers.Default
    override val io: CoroutineContext get() = Dispatchers.IO
    override val unconfined: CoroutineContext get() = Dispatchers.Unconfined
    @OptIn(ExperimentalCoroutinesApi::class)
    override val limitedParallelism: CoroutineContext get() = Dispatchers.Default.limitedParallelism(1)
}