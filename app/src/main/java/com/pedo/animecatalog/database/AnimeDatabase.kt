package com.pedo.animecatalog.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AnimeModel::class,AnimeStatModel::class],version = 1,exportSchema = false)
abstract class AnimeDatabase : RoomDatabase(){
    abstract val animeDao : AnimeDao
}