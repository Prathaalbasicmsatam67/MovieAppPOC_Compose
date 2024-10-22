package com.movieapppoc.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import com.movieapppoc.movielist.data.local.MovieDao
import com.movieapppoc.movielist.data.local.MovieDatabase
import com.movieapppoc.movielist.data.remote.MovieApi
import com.movieapppoc.movielist.util.network.ConnectivityProvider
import com.movieapppoc.movielist.util.network.ConnectivityProviderImpl
import com.movieapppoc.movielist.util.network.HttpExceptionInterceptor
import com.movieapppoc.movielist.util.network.HttpRetryInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Duration
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
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

    @Singleton
    @Provides
    fun providesMovieApi(): MovieApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(MovieApi.BASE_URL)
            .client(client)
            .build()
            .create(MovieApi::class.java)
    }

    @Provides
    @Singleton
    fun providesMovieDatabase(app: Application): MovieDatabase {
        return Room.databaseBuilder(
            app,
            MovieDatabase::class.java,
            "moviesdb.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideConnectivityProvider(app: Application): ConnectivityProvider {
        val connectivityManager =
            app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return ConnectivityProviderImpl(connectivityManager)
    }

    @Provides
    fun providesMovieDao(movieDatabase: MovieDatabase): MovieDao {
        return movieDatabase.movieDao
    }
}