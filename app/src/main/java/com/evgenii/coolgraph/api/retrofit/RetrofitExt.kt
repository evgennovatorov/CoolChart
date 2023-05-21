package com.evgenii.coolgraph.api.retrofit

inline fun <reified T> provide(retrofitProvider: RetrofitProvider) =
    retrofitProvider.retrofit.create(T::class.java)