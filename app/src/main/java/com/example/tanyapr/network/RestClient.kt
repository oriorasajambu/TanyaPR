/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RestClient {

    companion object {

        fun create(endPoint: String) : ApiClient {
            val retrofit = getClient(endPoint)
            return retrofit.create(ApiClient::class.java)
        }

        private fun getClient(endPoint : String) : Retrofit {
            val convertJson = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            val client = OkHttpClient.Builder().apply {
                readTimeout(60, TimeUnit.SECONDS)
                connectTimeout(60, TimeUnit.SECONDS)
                addInterceptor(convertJson)
                addInterceptor {
                    val origin = it.request()
                    val request = origin.newBuilder().build()
                    it.proceed(request)
                }
            }.build()

            val gsonBuilder = GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create()
            val gsonFactory = GsonConverterFactory.create(gsonBuilder)

            return Retrofit.Builder().apply {
                baseUrl(endPoint)
                addConverterFactory(gsonFactory)
                client(client)
            }.build()
        }

    }

}