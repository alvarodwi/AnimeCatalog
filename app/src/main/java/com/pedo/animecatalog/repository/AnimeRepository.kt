package com.pedo.animecatalog.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.pedo.animecatalog.database.AnimeDatabase
import com.pedo.animecatalog.database.AnimeStatModel
import com.pedo.animecatalog.domain.Anime
import com.pedo.animecatalog.utils.asDatabaseModel
import com.pedo.animecatalog.utils.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class AnimeRepository(private val database: AnimeDatabase) {
    //retrofit client/service
    private val jikanClient: JikanApiService = JikanApi.retrofitService

    val topAnimes: LiveData<List<Anime>> = Transformations.map(database.animeDao.getTopAnimes()) {
        it.asDomainModel()
    }

    suspend fun refreshTopAnime() {
        withContext(Dispatchers.IO) {
            val topLists = jikanClient.getTopAnime()
            database.animeDao.insertAll(topLists.asDatabaseModel())
        }
    }

    suspend fun getAnimes(page: Int, type: String) = jikanClient.getAll(page, type)

    suspend fun getAnime(id: Int) = jikanClient.getAnime(id)

    suspend fun markAsFavorite(anime: Anime) {
        withContext(Dispatchers.IO) {
            val animeSaved = anime.asDatabaseModel()
            database.animeDao.insertAnime(animeSaved)
            database.animeDao.insertAnimeStat(AnimeStatModel(animeSaved.id, true))
        }
    }

    suspend fun unMarkAsFavorite(anime: Anime) {
        withContext(Dispatchers.IO) {
            Timber.d("Trying to delete ${anime.id}")
            database.animeDao.deleteAnimeStat(anime.id)
        }
    }

    suspend fun getFavoriteAnime(): List<Anime> {
        return withContext(Dispatchers.IO){
            val filteredStat = database.animeDao.filterFavoriteAnime()
            val queue = filteredStat.map {
                database.animeDao.getAnime(it.id)
            }

            val result = queue.asDomainModel()
            result
        }
    }

    suspend fun findAnime(id: Int) = database.animeDao.filterAnime(id)
}