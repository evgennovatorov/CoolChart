package com.evgenii.coolgraph.api.retrofit

inline fun <reified T> provide(retrofitProvider: RetrofitProvider): T =
    retrofitProvider.retrofit.create(T::class.java)