package com.github.icarohs7.unoxandroidarch.extensions

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.serializationConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import timber.log.Timber

/**
 * Short hand version to create a retrofit instance
 * with the given [baseUrl] and with that create
 * an instance of a given service
 */
inline fun <reified T> createRetrofitService(baseUrl: String): T {
    val contentType = MediaType.get("application/json")
    val jsonAdapter = Json.nonstrict
    val serializationConverter = serializationConverterFactory(contentType, jsonAdapter)
    val interceptor = HttpLoggingInterceptor { Timber.tag("Retrofit").d(it) }
            .apply { level = HttpLoggingInterceptor.Level.BODY }
    val client = OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor(StethoInterceptor()).build()
    val retrofit = Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(serializationConverter)
            .client(client)
            .build()
    return retrofit.create(T::class.java)
}