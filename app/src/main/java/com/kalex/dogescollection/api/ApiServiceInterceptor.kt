package com.kalex.dogescollection.api

import okhttp3.Interceptor
import okhttp3.Response


/**
 * this will help to add the Auth token if it is needed
 *
 * @author Kevin Alexander (Kalex)
 * **/
object ApiServiceInterceptor : Interceptor {
    const val AUTH_HEADER_KEY = "authentication_requested"
    private const val AUTH_TOKEN_KEY = "AUTH-TOKEN"

    private var currentToken: String? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        if (chain.request().header(AUTH_HEADER_KEY) != null) {
            if (currentToken == null) throw java.lang.RuntimeException("Need to be authenticated")
            requestBuilder.addHeader(AUTH_TOKEN_KEY, currentToken!!)
        }
        return chain.proceed(requestBuilder.build())
    }
    fun setCurrentToken(token: String?){
        this.currentToken = token
    }

}
