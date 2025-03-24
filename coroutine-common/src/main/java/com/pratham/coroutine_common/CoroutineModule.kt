package com.pratham.coroutine_common

import com.pratham.coroutine_common.api.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoroutineModule {

    @Provides
    @Singleton
    fun bindDispatcherProvider(): DispatcherProvider = CoroutineDispatcherProvider()


}