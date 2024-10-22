package com.movieapppoc.common.kotlin.coroutine.di

import com.movieapppoc.common.kotlin.coroutine.api.DispatcherProvider
import com.movieapppoc.common.kotlin.coroutine.main.CoroutineDispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//abstract class CoroutineModule {
//
//    @Provides
//    @Singleton
//    fun bindDispatcherProvider(): DispatcherProvider = CoroutineDispatcherProvider()
//
//}