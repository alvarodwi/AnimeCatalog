package com.pedo.animecatalog.ui.detail

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.pedo.animecatalog.database.getDatabase
import com.pedo.animecatalog.domain.Anime
import com.pedo.animecatalog.repository.AnimeRepository
import com.pedo.animecatalog.utils.asDomainModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class AnimeDetailViewModel(movie: Anime, app: Application) : AndroidViewModel(app) {
    //coroutine
    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    //livedata
    private val _selectedMovie = MutableLiveData<Anime>()
    val selectedMovie : LiveData<Anime>
        get() = _selectedMovie
    private val _isFavorited = MutableLiveData<Boolean>()
    val isFavorited : LiveData<Boolean>
        get() = _isFavorited

    private val repository : AnimeRepository = AnimeRepository(getDatabase(app))

    init {
        _selectedMovie.value = movie
        syncDetails(movie.id)
    }

    private fun syncDetails(id : Int){
        viewModelScope.launch {
            try{
                _selectedMovie.value = repository.getAnime(id).asDomainModel()
                //if movie is already in database
                val existingMovie = repository.findAnime(id)
                _isFavorited.value = existingMovie.isLoved
            }catch (exception : Exception){
                Timber.e(exception)
            }
        }
    }

    val displayScore = Transformations.map(selectedMovie){
        "Score : ${it.score}"
    }

    fun markFavorite(){
        viewModelScope.launch {
            repository.markAsFavorite(_selectedMovie.value!!)
            _isFavorited.value = true
        }
    }
}