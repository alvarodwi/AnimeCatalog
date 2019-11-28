package com.pedo.animecatalog.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AnimeDao{
    @Query("SELECT * FROM anime WHERE id = :id")
    suspend fun getAnime(id : Int) : AnimeModel

    @Query("SELECT * FROM anime")
    fun getAnimes() : LiveData<List<AnimeModel>>

    @Query("SELECT * FROM anime WHERE rank <= 50 ORDER BY rank")
    fun getTopAnimes() : LiveData<List<AnimeModel>>

    @Query("SELECT * FROM anime_stat WHERE isLoved = :filter")
    fun filterFavoriteAnime(filter :Boolean = true) :List<AnimeStatModel>

    @Query("SELECT * FROM anime_stat WHERE id = :id")
    suspend fun filterAnime(id : Int) : AnimeStatModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAnime(anime : AnimeModel)

    @Insert()
    fun insertAnimeStat(stat : AnimeStatModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( videos: List<AnimeModel>)

    @Query("DELETE FROM anime WHERE id = :id")
    fun deleteAnime(id : Int)

    @Query("DELETE FROM anime")
    fun resetAnimeTable()

    @Query("DELETE FROM anime_stat WHERE id = :id")
    fun deleteAnimeStat(id : Int)
}