package com.pedo.animecatalog.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.pedo.animecatalog.database.AnimeDatabase
import com.pedo.animecatalog.repository.AnimeRepository

//db related
private lateinit var DB_INSTANCE : AnimeDatabase

fun getDatabase(context : Context): AnimeDatabase {
    synchronized(AnimeDatabase::class){
        if(!::DB_INSTANCE.isInitialized){
            DB_INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                AnimeDatabase::class.java,
                "anime-db"
            ).build()
        }
        return DB_INSTANCE
    }
}

//repository related
private lateinit var REPOSITORY_INSTANCE : AnimeRepository

fun getRepository(context : Context) : AnimeRepository{
    synchronized(AnimeRepository::class){
        if(!::REPOSITORY_INSTANCE.isInitialized){
            REPOSITORY_INSTANCE = AnimeRepository(context)
        }
        return REPOSITORY_INSTANCE
    }
}

//shared preferences related
private lateinit var SHARED_PREFERENCES_INSTANCE : SharedPreferences

fun getSharedPreferences(context: Context) : SharedPreferences {
    synchronized(SharedPreferences::class.java) {
        if (!::SHARED_PREFERENCES_INSTANCE.isInitialized) {
            SHARED_PREFERENCES_INSTANCE =
                context.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE)
        }
        return SHARED_PREFERENCES_INSTANCE
    }
}