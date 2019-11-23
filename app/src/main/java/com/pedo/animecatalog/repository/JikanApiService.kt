package com.pedo.animecatalog.repository

import com.pedo.animecatalog.network.JikanTopResponse
import com.pedo.animecatalog.network.NetworkAnime
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://api.jikan.moe/v3/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface JikanApiService{
    @GET("anime/{id}")
    suspend fun getAnime(@Path("id")id : Long) : NetworkAnime

    @GET("top/anime/{page}/{type}")
    suspend fun getAll(@Path("page")page : Int,@Path("type")type :String) : JikanTopResponse
}

object JikanApi{
    val retrofitService : JikanApiService by lazy {
        retrofit.create(JikanApiService::class.java)
    }
}