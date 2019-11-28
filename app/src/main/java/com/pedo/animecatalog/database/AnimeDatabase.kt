package com.pedo.animecatalog.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AnimeModel::class,AnimeStatModel::class],version = 1,exportSchema = false)
abstract class AnimeDatabase : RoomDatabase(){
    abstract val animeDao : AnimeDao
}

private lateinit var INSTANCE : AnimeDatabase

fun getDatabase(context : Context): AnimeDatabase{
    synchronized(AnimeDatabase::class){
        if(!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                AnimeDatabase::class.java,
                "anime-db"
            ).build()
        }
        return INSTANCE
    }
}