package com.github.icarohs7.unoxandroidarch.extensions

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Short hand version to create a retrofit instance
 * with the given [baseUrl] and with that create
 * an instance of a given service
 */
inline fun <reified T> createRetrofitService(baseUrl: String): T {
    return Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(RetrofitExtensions.getJsonConverter())
            .client(RetrofitExtensions.getHttpClient())
            .build()
            .create(T::class.java)
}

object RetrofitExtensions {
    fun getJsonConverter(): Converter.Factory {
        val contentType = MediaType.get("application/json")
        return Json
                .nonstrict
                .asConverterFactory(contentType)
    }

    fun getHttpClient(): OkHttpClient {
        return OkHttpClient
                .Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(getInterceptor())
                .addInterceptor(StethoInterceptor())
                .build()
    }

    private fun getInterceptor(): HttpLoggingInterceptor {
        val log = { s: String -> Timber.tag("Retrofit").d(s) }
        return HttpLoggingInterceptor(log)
                .apply { level = HttpLoggingInterceptor.Level.BODY }
    }
}