package com.pedo.animecatalog.ui.animedetail

import android.app.Application
import androidx.lifecycle.*
import com.pedo.animecatalog.domain.Anime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class AnimeDetailViewModel(movie: Anime, app: Application) : AndroidViewModel(app) {
    //coroutine
    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Default)

    //livedata
    private val _selectedMovie = MutableLiveData<Anime>()
    val selectedMovie : LiveData<Anime>
        get() = _selectedMovie

    init {
        _selectedMovie.value = movie
    }

    val displayScore = Transformations.map(selectedMovie){
        "Score : ${it.score}"
    }
}