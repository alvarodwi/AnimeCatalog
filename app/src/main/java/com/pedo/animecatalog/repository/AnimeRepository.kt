package com.pedo.animecatalog.repository

import com.pedo.animecatalog.database.AnimeDatabase
import com.pedo.animecatalog.domain.Anime
import com.pedo.animecatalog.network.NetworkAnime
import com.pedo.animecatalog.utils.asDatabaseModel
import com.pedo.animecatalog.utils.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AnimeRepository(private val database : AnimeDatabase) {
    //retrofit client/service
    val jikanClient : JikanApiService = JikanApi.retrofitService

    suspend fun getTopAnime(page : Int,type : String) = jikanClient.getAll(page,type)

    suspend fun getAnime(id : Long) = jikanClient.getAnime(id)

    suspend fun saveAnimeAsLoved(anime : NetworkAnime){
        withContext(Dispatchers.IO){
            val animeSaved = anime.asDatabaseModel()
            animeSaved.isLoved = true
            database.animeDao.insertAnime(animeSaved)
        }
    }

    suspend fun saveSelectedAnime(){
        withContext(Dispatchers.IO){

        }
    }
}