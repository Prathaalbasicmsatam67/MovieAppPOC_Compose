package com.pratham.networkmodule.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.pratham.networkmodule.BaseUrl
import com.pratham.networkmodule.ConnectivityProvider
import com.pratham.networkmodule.ConnectivityProviderImpl
import com.pratham.networkmodule.HttpExceptionInterceptor
import com.pratham.networkmodule.HttpRetryInterceptor
import com.pratham.networkmodule.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
//        interceptors.forEach { interceptor: Interceptor -> builder.addInterceptor(interceptor) }

        return builder
            .addInterceptor(interceptor)
            .addInterceptor(HttpExceptionInterceptor())
            .addInterceptor(HttpRetryInterceptor())
            .addNetworkInterceptor(
                interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .connectTimeout(3L, TimeUnit.SECONDS)
            .readTimeout(3L, TimeUnit.SECONDS)
            .writeTimeout(3L, TimeUnit.SECONDS)
            .build()
    }


    @Provides
    fun providesAPIService(
        okHttpClient: OkHttpClient,
        @BaseUrl baseUrl: String
    ) = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .build()
        .create(MovieApi::class.java)

    @Provides
    @Singleton
    fun provideConnectivityProvider(app: Application): ConnectivityProvider {
        val connectivityManager =
            app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return ConnectivityProviderImpl(connectivityManager)
    }
}