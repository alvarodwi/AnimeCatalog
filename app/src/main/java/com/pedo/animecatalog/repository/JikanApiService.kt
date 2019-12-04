package com.pedo.animecatalog.repository

import com.pedo.animecatalog.network.JikanSeasonArchiveResponse
import com.pedo.animecatalog.network.JikanSeasonResponse
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
    //get anime by id
    @GET("anime/{id}")
    suspend fun getAnime(@Path("id")id : Int) : NetworkAnime

    //get top anime
    @GET("top/anime")
    suspend fun getTopAnime() : JikanTopResponse

    //get top anime with pages and subtype
    @GET("top/anime/{page}/{subType}")
    suspend fun getAll(@Path("page")page : Int,@Path("subType")subType :String) : JikanTopResponse

    //get seasonal anime by year and season
    @GET("season/{year}/{season}")
    suspend fun getSeasonalAnime(@Path("year")year: Int,@Path("season")season: String) : JikanSeasonResponse

    //get anime that have announced for 'next' season
    @GET("season/later")
    suspend fun getSeasonLaterAnime() : JikanSeasonResponse

    //get season archive (to get latest year available)
    @GET("season/archive")
    suspend fun getSeasonArchive() : JikanSeasonArchiveResponse
}

object JikanApi{
    val retrofitService : JikanApiService by lazy {
        retrofit.create(JikanApiService::class.java)
    }
}