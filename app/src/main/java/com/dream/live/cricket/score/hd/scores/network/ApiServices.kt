package com.dream.live.cricket.score.hd.scores.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import com.dream.live.cricket.score.hd.BuildConfig
import com.dream.live.cricket.score.hd.scores.utility.Cons.base_url_scores
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object ApiServices {
    private val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val client: OkHttpClient by lazy {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        OkHttpClient.Builder()
            .readTimeout(90, TimeUnit.SECONDS)
            .writeTimeout(90, TimeUnit.SECONDS)
            .connectTimeout(90, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addInterceptor(HeaderInterceptor())
            //.addInterceptor(RetrofitInterceptor("packageName"))
//                .authenticator(RetrofitAuthenticator())
            .build()
    }


    private val retrofit: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(base_url_scores)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())

    }
    private val retrofit2: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(base_url_scores)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    val retrofitService: Interfaces by lazy {
        retrofit
            .build()
            .create(Interfaces::class.java)
    }
    val retrofitService2: Interfaces by lazy {
        retrofit2
            .build()
            .create(Interfaces::class.java)
    }

}