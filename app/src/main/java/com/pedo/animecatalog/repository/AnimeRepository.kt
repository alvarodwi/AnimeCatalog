package com.pedo.animecatalog.repository

import androidx.lifecycle.LiveData
import com.pedo.animecatalog.database.AnimeDatabase
import com.pedo.animecatalog.database.AnimeModel
import com.pedo.animecatalog.domain.Anime
import com.pedo.animecatalog.network.NetworkAnime
import com.pedo.animecatalog.utils.asDatabaseModel
import com.pedo.animecatalog.utils.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class AnimeRepository(private val database : AnimeDatabase) {
    //retrofit client/service
    val jikanClient : JikanApiService = JikanApi.retrofitService

    suspend fun getTopAnime(page : Int,type : String) = jikanClient.getAll(page,type)

    suspend fun getAnime(id : Int) = jikanClient.getAnime(id)

    suspend fun markAsFavorite(anime : Anime){
        withContext(Dispatchers.IO){
            val animeSaved = anime.asDatabaseModel()
            animeSaved.isLoved = true
            database.animeDao.insertAnime(animeSaved)
        }
    }

    suspend fun findAnime(id : Int) = database.animeDao.getAnime(id)

    fun showAllAnimes() = database.animeDao.getAnimes()
}