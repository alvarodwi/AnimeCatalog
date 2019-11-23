package com.pedo.animecatalog.network

import com.squareup.moshi.Json

data class NetworkGenre(
    @Json(name = "mal_id") val id : Int,
    val type : String,
    val name : String,
    val url : String
)

data class NetworkStudio(
    @Json(name = "mal_id") val id : Int,
    val type : String,
    val name : String,
    val url : String
)

data class NetworkAired (
    val from : String,
    val to : String,
    val prop : NetworkProp,
    val string : String
)

data class NetworkProp (
    val from : NetworkFrom,
    val to : NetworkTo
)

data class NetworkFrom (
    val day : String,
    val month : String,
    val year : String
)

data class NetworkTo (
    val day : String,
    val month : String,
    val year : String
)