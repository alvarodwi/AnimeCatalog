package com.pedo.animecatalog.utils

import android.content.SharedPreferences
import androidx.lifecycle.LiveData

//as mentioned in https://stackoverflow.com/questions/50649014/livedata-with-shared-preferences

abstract class SharedPreferenceLiveData<T>(
    private val sharedPrefs: SharedPreferences,
    private val key: String,
    private val defValue: T
) : LiveData<T>() {

    private val preferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == this.key) {
                value = getValueFromPreferences(key, defValue)
            }
        }

    abstract fun getValueFromPreferences(key: String, defValue: T): T

    override fun onActive() {
        super.onActive()
        value = getValueFromPreferences(key, defValue)
        sharedPrefs.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
    }

    override fun onInactive() {
        sharedPrefs.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener)
        super.onInactive()
    }

    fun getStringLiveData(key : String,defValue: String) : SharedPreferenceLiveData<String>{
        return SharedPreferenceStringLiveData(sharedPrefs,key,defValue)
    }
}

class SharedPreferenceStringLiveData(private val prefs : SharedPreferences, key :String, defValue: String) : SharedPreferenceLiveData<String>(prefs,key,defValue){
    override fun getValueFromPreferences(key: String, defValue: String): String {
        return prefs.getString(key,defValue)!!
    }

}