package com.pedo.animecatalog.ui.list.season

import android.app.Application
import androidx.lifecycle.*
import com.pedo.animecatalog.domain.Anime
import com.pedo.animecatalog.repository.AnimeRepository
import com.pedo.animecatalog.utils.AnimeListingStatus
import com.pedo.animecatalog.utils.getRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import timber.log.Timber

class AnimeSeasonViewModel(private val year : String, private val season : String, app : Application) : AndroidViewModel(app) {
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
    private val _status = MutableLiveData<AnimeListingStatus>()
    val status : LiveData<AnimeListingStatus>
        get() = _status
    private val _page = MutableLiveData<Int>()
    val page : LiveData<Int>
        get() = _page

    private val repository: AnimeRepository = getRepository(app)

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


    class Factory(val year : String,val season : String,val app: Application) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            if(modelClass.isAssignableFrom(AnimeSeasonViewModel::class.java)){
                return AnimeSeasonViewModel(year,season,app) as T
            }
            throw IllegalArgumentException("Unable To Construct ViewModel")
        }

    }
}
