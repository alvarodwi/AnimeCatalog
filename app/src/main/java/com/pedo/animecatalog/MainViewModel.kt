package com.pedo.animecatalog

import android.app.Application
import androidx.core.content.edit
import androidx.lifecycle.*
import com.pedo.animecatalog.utils.TYPE_LIST
import com.pedo.animecatalog.utils.VIEW_MODE_KEY
import com.pedo.animecatalog.utils.getSharedPreferences
import timber.log.Timber

class MainViewModel(app : Application) : AndroidViewModel(app){
    private val _viewMode = MutableLiveData<String>()
    private val _oldViewMode = MutableLiveData<String>()
    private val mSharedPreferences = getSharedPreferences(app.applicationContext)

    val viewMode : LiveData<String>
        get() = _viewMode

    init{
        getLastViewModePreferences()
        Timber.d(_viewMode.value)
    }

    private fun getLastViewModePreferences(){
        _viewMode.value = mSharedPreferences.getString(VIEW_MODE_KEY, TYPE_LIST)
        _oldViewMode.value = _viewMode.value
    }

    fun changeViewMode(viewMode : String){
        _oldViewMode.value = _viewMode.value
        _viewMode.value = viewMode
        mSharedPreferences.edit { putString(VIEW_MODE_KEY,viewMode) }
    }

    fun hasViewModeChanged(valueToCompare : String?) : Boolean{
        Timber.d("Has View Mod -> Old : $valueToCompare and New : ${_viewMode.value}")
        return valueToCompare != _viewMode.value
    }

    class Factory(val app: Application) : ViewModelProvider.Factory{
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(MainViewModel::class.java)){
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unknown View Model")
        }

    }
}