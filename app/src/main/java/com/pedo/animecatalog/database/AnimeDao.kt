package com.pedo.animecatalog.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AnimeDao{
    @Query("SELECT * FROM anime WHERE id = :id")
    fun getAnime(id : Int) : LiveData<AnimeModel>

    @Query("SELECT * FROM anime")
    fun getAnimes() : LiveData<List<AnimeModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAnime(anime : AnimeModel)

    @Update
    fun updateAnime(anime: AnimeModel)

    @Query("DELETE FROM anime WHERE id = :id")
    fun deleteAnime(id : Int)

    @Query("DELETE FROM anime")
    fun resetAnimeTable()
}