package com.pratham.networkmodule

import com.pratham.networkmodule.exceptions.ClientErrorException
import com.pratham.networkmodule.exceptions.ServerErrorException
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class HttpExceptionInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val response: Response = chain.proceed(original)

        // Check for HTTP 4xx and 5xx status codes
        if (!response.isSuccessful) {
            if (response.code in SERVER_ERROR_CODE_400..SERVER_ERROR_CODE_499) {
                throw ClientErrorException(errorCode = response.code)
            } else if (response.code in SERVER_ERROR_CODE_500..SERVER_ERROR_CODE_599) {
                throw ServerErrorException(response.code)
            }
        }
        return response
    }

    companion object {
        const val SERVER_ERROR_CODE_400 = 400
        const val SERVER_ERROR_CODE_499 = 499
        const val SERVER_ERROR_CODE_500 = 500
        const val SERVER_ERROR_CODE_599 = 599
    }
}