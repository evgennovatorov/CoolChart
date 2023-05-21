package com.evgenii.coolgraph.api.retrofit

import com.evgenii.coolgraph.api.okhttp.OkhttpProvider
import okhttp3.OkHttp
import retrofit2.Retrofit

private const val URL = "https://hr-challenge.interactivestandard.com/"

class InteractiveRetrofitProvider(
    okhttpProvider: OkhttpProvider
): RetrofitProvider {
    override val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(URL)
            .client(okhttpProvider.client)
            .addConverterFactory(RetrofitGsonConverterFactory.createFactory())
            .build()
    }

}