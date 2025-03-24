package com.pratham.networkmodule

import android.os.Handler
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import kotlin.math.pow

class HttpRetryInterceptor(
    private val maxRetries: Int = 3,         // Maximum retry attempts
    private val retryDelayMs: Long = 500     // Initial retry delay in milliseconds
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        var response: Response
        var tryCount = 0

        while (true) {
            try {
                response = chain.proceed(request)
                if (response.isSuccessful) {
                    return response // ✅ Success, return response
                }
            } catch (e: IOException) {
                Log.w(TAG, "Request failed, retrying... ($tryCount/$maxRetries)", e)
            }

            if (tryCount >= maxRetries) {
                throw IOException("Failed after $maxRetries retries")
            }

            tryCount++

            // Exponential backoff delay (2^tryCount * initial delay)
            val backoffDelay = retryDelayMs * 2.0.pow(tryCount.toDouble()).toLong()
            Log.d(TAG, "Retry #$tryCount in ${backoffDelay}ms")

            // ✅ Sleep for the backoff delay before retrying (works in background threads)
            Thread.sleep(backoffDelay)
        }
    }

    companion object {
        private const val TAG = "RetryInterceptor"
    }
}