package com.evgenii.coolgraph.api.okhttp

import com.evgenii.coolgraph.common.AppLogger
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class InteractiveOkHttpProvider: OkhttpProvider {
    override val client by lazy {
        OkHttpClient.Builder()
            .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            })
            .build()
    }

}