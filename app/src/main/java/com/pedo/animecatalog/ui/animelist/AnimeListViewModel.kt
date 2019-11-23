package com.pedo.animecatalog.ui.animelist

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.pedo.animecatalog.database.AnimeDatabase
import com.pedo.animecatalog.database.getDatabase
import com.pedo.animecatalog.domain.Anime
import com.pedo.animecatalog.repository.AnimeRepository
import com.pedo.animecatalog.utils.asDomainModels
import kotlinx.coroutines.*
import org.w3c.dom.DOMConfiguration
import timber.log.Timber

enum class JikanApiStatus{
    LOADING,DONE,ERROR
}

class AnimeListViewModel(app : Application) : AndroidViewModel(app) {
    //coroutine
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    //livedata
    //movies
    private val _movies = MutableLiveData<List<Anime>>()
    val movies : LiveData<List<Anime>>
        get() = _movies
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
        getAnimes(1,"tv")
    }

    //getAllMovieFromJikanApi
    fun getAnimes(page : Int,type : String){
        uiScope.launch {
            Timber.d("Top Anime GET")
            try{
                _status.value = JikanApiStatus.LOADING
                val getMovies = repository.getTopAnime(page,type)
                _movies.value = getMovies.asDomainModels()
                _status.value = JikanApiStatus.DONE
            }catch (e: Exception){
                Timber.d("API FAIL")
                _status.value = JikanApiStatus.ERROR
                _movies.value = ArrayList()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun displayMovieDetail(movie: Anime){
        Timber.d("Anime : ${movie.toString()}")
        _navigateToDetail.value = movie
    }

    fun displayMovieDetailCompleted(){
        _navigateToDetail.value = null
    }

    class Factory(val app: Application) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            if(modelClass.isAssignableFrom(AnimeListViewModel::class.java)){
                return AnimeListViewModel(app) as T
            }
            throw IllegalArgumentException("Unable To Construct ViewModel")
        }

    }
}