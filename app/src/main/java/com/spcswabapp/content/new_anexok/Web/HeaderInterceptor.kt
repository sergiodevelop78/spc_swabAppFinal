package com.spcswabapp.content.new_anexok.Web

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "32ec7a8f-b657-4385-92fd-4e949c530ee8")
            .build()
        return chain.proceed(request)
    }

}