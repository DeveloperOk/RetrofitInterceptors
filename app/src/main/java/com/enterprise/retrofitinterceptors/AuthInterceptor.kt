package com.enterprise.retrofitinterceptors

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val currentRequest = chain.request().newBuilder()

        val AUTHORIZATION = "Authorization"

        val BEARER = "Bearer"
        val TOKEN = "1234ABCD"

        //There is space between bearer and token
        val BEARERandTOKEN = "$BEARER $TOKEN"

        currentRequest.addHeader(AUTHORIZATION, BEARERandTOKEN)

        val newRequest = currentRequest.build()
        return chain.proceed(newRequest)

    }
}