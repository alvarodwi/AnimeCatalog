package com.pedo.animecatalog.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Anime(
    val id : Int,
    val rank : Int,
    val url : String,
    val imageUrl : String,
    val title : String,
    val type : String,
    val episodes : Int = 0,
    val members : Int,
    val score : Double,
    val trailerUrl : String? = null,
    val titleEnglish : String? = null,
    val titleJapanese : String? = null,
    val titleSynonyms : List<String>? = null,
    val source : String? = null,
    val status : String? = null,
    val airing : Boolean? = null,
    val aired : String? = null,
    val duration : String? = null,
    val rating : String? = null,
    val scoredBy : Int? = null,
    val popularity : Int? = null,
    val favorites : Int? = null,
    val synopsis : String? = null,
    val background : String? = null,
    val premiered : String? = null,
    val broadcast : String? = null,
    val studios : List<String>? = null,
    val genres : List<String>? = null,
    val openingThemes : List<String>? = null,
    val endingThemes : List<String>? = null
) : Parcelable