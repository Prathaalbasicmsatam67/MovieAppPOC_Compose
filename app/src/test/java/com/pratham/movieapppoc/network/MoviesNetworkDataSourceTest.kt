package com.pratham.movieapppoc.network

import com.movieapppoc.movielist.data.remote.MovieApi
import com.movieapppoc.movielist.data.remote.MovieRemoteDataSource
import com.pratham.networkmodule.response.MovieDto
import com.pratham.networkmodule.response.MovieListDto
import com.pratham.movieapppoc.extensions.enqueueResponse
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit


@ExperimentalCoroutinesApi
//@ExperimentalSerializationApi
class MoviesNetworkDataSourceTest {

    private var remoteDataSource: MovieRemoteDataSource? = null
    private lateinit var mockWebServer: MockWebServer

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()


    @Before
    @Throws(IOException::class)
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApi::class.java)

        remoteDataSource = MovieRemoteDataSource(api)
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `should fetch movies correctly given 200 response`() = runBlocking {

        mockWebServer.enqueueResponse("movies-200.json", 200)


        val actual = remoteDataSource?.getMoviesList("", 1)
        mockWebServer.takeRequest()
        val expected =
            MovieListDto(
                page = 1,
                total_pages = 500,
                results = listOf(
                    MovieDto(
                        id = 464052,
                        overview = "Black Manta, still driven by the need to avenge his father's death and wielding the power of the mythic Black Trident, will stop at nothing to take Aquaman down once and for all. To defeat him, Aquaman must turn to his imprisoned brother Orm, the former King of Atlantis, to forge an unlikely alliance in order to save the world from irreversible destruction.",
                        title = "Aquaman and the Lost Kingdom",
                        vote_average = 7.2,
                        poster_path = "/8UlWHLMpgZm9bx6QYh0NFoq67TZ.jpg"
                    )
                ),
                total_results = 10000
            )


        assertEquals(expected.results[0].title, actual?.results?.get(0)?.title ?: "")

    }
}