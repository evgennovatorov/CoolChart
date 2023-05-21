package com.evgenii.coolgraph.api.retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitGsonConverterFactory {

    fun createFactory() : Converter.Factory {
        val gson = createGson()
        return GsonConverterFactory.create(gson)
    }

    private fun createGson(): Gson {
        return GsonBuilder().create()
    }
}