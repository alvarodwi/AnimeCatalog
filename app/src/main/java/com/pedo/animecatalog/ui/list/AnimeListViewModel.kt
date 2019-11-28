package com.pedo.animecatalog.ui.list

import android.app.Application
import androidx.lifecycle.*
import com.pedo.animecatalog.database.getDatabase
import com.pedo.animecatalog.domain.Anime
import com.pedo.animecatalog.repository.AnimeRepository
import com.pedo.animecatalog.utils.AnimeListingStatus
import com.pedo.animecatalog.utils.asDomainModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

//generic -- api based -- view model
//used in airing,upcoming,types
class AnimeListViewModel(val animeType : String,app : Application) : AndroidViewModel(app) {
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

    private val repository : AnimeRepository = AnimeRepository(getDatabase(app))

    init{
        initAnimes(animeType)
    }

    private fun initAnimes(type : String){
        viewModelScope.launch {
            try{
                _status.value = AnimeListingStatus.LOADING
                val apiAnimes = repository.getAnimes(1,type)
                _animes.value = apiAnimes.asDomainModel()
                _page.value = 1
                _status.value = AnimeListingStatus.DONE
            }catch (e: Exception){
                Timber.e(e)
                Timber.d("API FAIL")
                _status.value = AnimeListingStatus.ERROR
                _animes.value = ArrayList()
            }
        }
    }

    //getAllMovieFromJikanApi
    private fun loadAnimes(page : Int,type : String){
        viewModelScope.launch {
            try{
                _status.value = AnimeListingStatus.LOADING
                val apiAnimes = repository.getAnimes(page,type)
                val existingAnimes = _animes.value as ArrayList<Anime>
                existingAnimes.addAll(apiAnimes.asDomainModel())
                _animes.value = existingAnimes
                _status.value = AnimeListingStatus.DONE
            }catch (e: Exception){
                Timber.d("API FAIL")
                _status.value = AnimeListingStatus.ERROR
                _animes.value = ArrayList()
            }
        }
    }

    fun onRefresh(){
        initAnimes(animeType)
    }

    fun onLoadMore(){
        _page.value?.plus(1)
        val pageNumber = requireNotNull(_page.value)
        loadAnimes(pageNumber,animeType)
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