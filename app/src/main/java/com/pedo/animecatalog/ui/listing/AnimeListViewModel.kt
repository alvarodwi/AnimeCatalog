package com.pedo.animecatalog.ui.listing

import android.app.Application
import androidx.lifecycle.*
import com.pedo.animecatalog.database.getDatabase
import com.pedo.animecatalog.domain.Anime
import com.pedo.animecatalog.repository.AnimeRepository
import com.pedo.animecatalog.utils.asDomainModels
import kotlinx.coroutines.*
import timber.log.Timber

enum class JikanApiStatus{
    LOADING,DONE,ERROR
}

class AnimeListViewModel(animeType : String,app : Application) : AndroidViewModel(app) {
    //coroutine
    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    //livedata
    //animes
    private val _animes = MutableLiveData<List<Anime>>()
    val animes : LiveData<List<Anime>>
        get() = _animes
    //selected movie todetail
    private val _navigateToDetail = MutableLiveData<Anime>()
    val navigateToDetail : LiveData<Anime>
        get() = _navigateToDetail
    //request status (api status)
    private val _status = MutableLiveData<JikanApiStatus>()
    val status : LiveData<JikanApiStatus>
        get() = _status

    private val repository : AnimeRepository = AnimeRepository(getDatabase(app))

    init{
        getAnimes(1,animeType)
    }

    //getAllMovieFromJikanApi
    private fun getAnimes(page : Int,type : String){
        viewModelScope.launch {
            Timber.d("Top Anime GET")
            try{
                _status.value = JikanApiStatus.LOADING
                val getMovies = repository.getTopAnime(page,type)
                _animes.value = getMovies.asDomainModels()
                _status.value = JikanApiStatus.DONE
            }catch (e: Exception){
                Timber.e(e)
                Timber.d("API FAIL")
                _status.value = JikanApiStatus.ERROR
                _animes.value = ArrayList()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun displayMovieDetail(movie: Anime){
        Timber.d("Anime : ${movie.title}, url = ${movie.url}")
        _navigateToDetail.value = movie
    }

    fun displayMovieDetailCompleted(){
        _navigateToDetail.value = null
    }

    class Factory(val type : String,val app: Application) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            if(modelClass.isAssignableFrom(AnimeListViewModel::class.java)){
                return AnimeListViewModel(type,app) as T
            }
            throw IllegalArgumentException("Unable To Construct ViewModel")
        }

    }
}