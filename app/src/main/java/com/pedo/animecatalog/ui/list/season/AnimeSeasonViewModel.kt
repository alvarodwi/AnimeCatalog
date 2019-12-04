package com.pedo.animecatalog.ui.list.season

import android.app.Application
import androidx.lifecycle.*
import com.pedo.animecatalog.domain.Anime
import com.pedo.animecatalog.repository.AnimeRepository
import com.pedo.animecatalog.utils.AnimeListingStatus
import com.pedo.animecatalog.utils.asDomainModel
import com.pedo.animecatalog.utils.getRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class AnimeSeasonViewModel(app : Application) : AndroidViewModel(app) {
    //coroutine
    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    //livedata
    private val mYear : Int = 2019
    private val mSeason : String = "fall"
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
    val oldViewMode = MutableLiveData<String>()

    private val repository: AnimeRepository = getRepository(app)

    init{
        getSeasonalAnime(mYear,mSeason)
    }

    private fun getSeasonalAnime(year : Int,season : String){
        viewModelScope.launch {
            try{
                _status.value = AnimeListingStatus.LOADING
                val apiAnimes = repository.getSeasonalAnimes(year,season)
                _animes.value = apiAnimes.asDomainModel()
                _status.value = AnimeListingStatus.DONE
            }catch (e: Exception){
                Timber.e(e)
                Timber.d("API FAIL")
                _status.value = AnimeListingStatus.ERROR
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

    fun onRefresh(){
        getSeasonalAnime(mYear,mSeason)
    }

    fun setOldData(viewMode : String){
        oldViewMode.value = viewMode
    }

    class Factory(val app: Application) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            if(modelClass.isAssignableFrom(AnimeSeasonViewModel::class.java)){
                return AnimeSeasonViewModel(app) as T
            }
            throw IllegalArgumentException("Unable To Construct ViewModel")
        }

    }
}
