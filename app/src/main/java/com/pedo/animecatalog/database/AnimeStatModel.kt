package com.pedo.animecatalog.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime_stat")
data class AnimeStatModel(
    @PrimaryKey val id: Int,
    var isLoved : Boolean = false,
    var hasWatched : Boolean = false
)