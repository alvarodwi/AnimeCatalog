package com.pedo.animecatalog.network

import com.squareup.moshi.Json

data class NetworkSeason (
    @Json(name = "year")val seasonYear : Int,
    @Json(name = "seasons")val seasonList : List<String>
)