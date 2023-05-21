package com.evgenii.coolgraph.api.okhttp

import okhttp3.OkHttpClient

interface OkhttpProvider {
    val client: OkHttpClient
}