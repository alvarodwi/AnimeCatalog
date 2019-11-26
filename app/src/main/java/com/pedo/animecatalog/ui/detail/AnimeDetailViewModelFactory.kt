package com.pedo.animecatalog.ui.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pedo.animecatalog.domain.Anime

class AnimeDetailViewModelFactory(
    private val movie: Anime,
    private val application : Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("unchecked_cast")
        if(modelClass.isAssignableFrom(AnimeDetailViewModel::class.java)) {
            return AnimeDetailViewModel(movie,application) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel Class")
    }
}