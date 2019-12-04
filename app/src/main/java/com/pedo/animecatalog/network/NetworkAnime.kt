package com.pedo.animecatalog.network

import com.squareup.moshi.Json

data class NetworkAnime(
    @Json(name = "mal_id") val id : Int,
    val rank : Int?, //anticipating season response that not carrying rank
    val title : String,
    val url : String,
    @Json(name = "image_url") val imageUrl : String,
    val type : String,
    val episodes : Int?, //anticipating upcoming anime with unknown episode
    val members : Int,
    val score : Double?, //anticipating some unknown anime with no score
    @Json(name = "trailer_url") val trailerUrl : String?,
    @Json(name = "title_english") val titleEnglish : String?,
    @Json(name = "title_japanese") val titleJapanese : String?,
    @Json(name = "title_synonyms") val titleSynonyms : List<String>?,
    val source : String?,
    val status : String?,
    val airing : Boolean?,
    val aired : NetworkAired?,
    val duration : String?,
    val rating : String?,
    @Json(name = "scored_by") val scoredBy : Int?,
    val popularity : Int?,
    val favorites : Int?,
    val synopsis : String?,
    val background : String?,
    val premiered : String?,
    val broadcast : String?,
    val studios : List<NetworkStudio>?,
    val genres : List<NetworkGenre>?,
    @Json(name = "opening_themes") val openingThemes : List<String>?,
    @Json(name = "ending_themes") val endingThemes : List<String>?,
    //to accept season response
    val r18 : Boolean?,
    val kids : Boolean?,
    val continuing : Boolean?
)