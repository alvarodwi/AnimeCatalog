package com.pedo.animecatalog.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime")
data class AnimeModel(
    @PrimaryKey val id : Int,
    val rank : Int,
    val title : String,
    val url : String,
    val imageUrl : String,
    val type : String,
    val episodes : Int,
    val members : Int,
    val score : Double,
    val trailerUrl : String? = null,
    val titleEnglish : String? = null,
    val titleJapanese : String? = null,
    val titleSynonyms : String? = null,
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
    val studios : String? = null,
    val genres : String? = null,
    val openingThemes : String? = null,
    val endingThemes : String? = null
)