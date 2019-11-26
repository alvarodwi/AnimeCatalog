package com.pedo.animecatalog.network

import com.squareup.moshi.Json

data class JikanTopResponse(
    @Json(name= "top")val animeList : List<NetworkAnime>
)

data class JikanSeasonResponse(
    @Json(name = "season_year")val year : String,
    @Json(name = "season_name")val season : String,
    @Json(name = "anime") val animeList : List<NetworkAnime>
)