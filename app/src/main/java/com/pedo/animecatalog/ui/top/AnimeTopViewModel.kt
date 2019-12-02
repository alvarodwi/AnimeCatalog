package com.pedo.animecatalog.ui.top

import android.app.Application
import androidx.lifecycle.*
import com.pedo.animecatalog.domain.Anime
import com.pedo.animecatalog.repository.AnimeRepository
import com.pedo.animecatalog.utils.AnimeListingStatus
import com.pedo.animecatalog.utils.getRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class AnimeTopViewModel(app: Application) : AndroidViewModel(app) {
    //coroutine
    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val repository: AnimeRepository = getRepository(app)
    //livedata
    //animes
    val animes = repository.topAnimes
    //selected movie todetail
    private val _navigateToDetail = MutableLiveData<Anime>()
    val navigateToDetail: LiveData<Anime>
        get() = _navigateToDetail
    //request status (api status)
    private val _status = MutableLiveData<AnimeListingStatus>()

    val oldViewMode = MutableLiveData<String>()

    val status: LiveData<AnimeListingStatus>
        get() = _status

    init {
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                _status.value = AnimeListingStatus.LOADING
                repository.refreshTopAnime()
                _status.value = AnimeListingStatus.DONE
            } catch (e: Exception) {
                Timber.e(e.localizedMessage)
                _status.value = AnimeListingStatus.ERROR
            }
        }
    }

    fun onRefresh() {
        refreshDataFromRepository()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun displayMovieDetail(movie: Anime) {
        Timber.d("Anime : ${movie.title}, url = ${movie.url}")
        _navigateToDetail.value = movie
    }

    fun displayMovieDetailCompleted() {
        _navigateToDetail.value = null
    }

    fun setOldData(viewMode : String){
        oldViewMode.value = viewMode
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            if (modelClass.isAssignableFrom(AnimeTopViewModel::class.java)) {
                return AnimeTopViewModel(app) as T
            }
            throw IllegalArgumentException("Unable To Construct ViewModel")
        }

    }
}