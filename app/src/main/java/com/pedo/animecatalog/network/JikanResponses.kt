package com.pedo.animecatalog.network

import com.squareup.moshi.Json

data class JikanTopResponse(
    @Json(name= "top")val animeList : List<NetworkAnime>
)