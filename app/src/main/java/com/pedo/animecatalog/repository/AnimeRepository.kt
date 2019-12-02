package com.pedo.animecatalog.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.pedo.animecatalog.database.AnimeStatModel
import com.pedo.animecatalog.domain.Anime
import com.pedo.animecatalog.utils.asDatabaseModel
import com.pedo.animecatalog.utils.asDomainModel
import com.pedo.animecatalog.utils.getDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class AnimeRepository(context : Context) {
    //retrofit client/service
    private val database = getDatabase(context)
    private val jikanClient: JikanApiService = JikanApi.retrofitService

    //get top animes from database
    val topAnimes: LiveData<List<Anime>> = Transformations.map(database.animeDao.getTopAnimes()) {
        it.asDomainModel()
    }

    //refresh top anime from api
    suspend fun refreshTopAnime() {
        withContext(Dispatchers.IO) {
            val topLists = jikanClient.getTopAnime()
            database.animeDao.insertAll(topLists.asDatabaseModel())
        }
    }

    //get animelist,depending on type (also paging) from api
    suspend fun getAnimes(page: Int, type: String) = jikanClient.getAll(page, type)

    //get anime depending on anime ID from api
    suspend fun getAnime(id: Int) = jikanClient.getAnime(id)

    //add anime as favorite, also adding it into database
    suspend fun markAsFavorite(anime: Anime) {
        withContext(Dispatchers.IO) {
            val animeSaved = anime.asDatabaseModel()
            database.animeDao.insertAnime(animeSaved)
            database.animeDao.insertAnimeStat(AnimeStatModel(animeSaved.id, true))
        }
    }

    //remove from favorite, only deleting stat, anime object still in database
    suspend fun unMarkAsFavorite(anime: Anime) {
        withContext(Dispatchers.IO) {
            Timber.d("Trying to delete ${anime.id}")
            database.animeDao.deleteAnimeStat(anime.id)
        }
    }

    //get favorite anime from database
    suspend fun getFavoriteAnime(): List<Anime> {
        return withContext(Dispatchers.IO){
            val filteredStat = database.animeDao.getFavoriteAnime()
            val queue = filteredStat.map {
                database.animeDao.getAnime(it.id)
            }

            val result = queue.asDomainModel()
            result
        }
    }

    //get anime by stat from database
    suspend fun findAnime(id: Int) = database.animeDao.getAnimeStat(id)
}