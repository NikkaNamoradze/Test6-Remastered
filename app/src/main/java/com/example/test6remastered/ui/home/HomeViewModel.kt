package com.example.test6remastered.ui.home

import androidx.lifecycle.ViewModel
import com.example.test6remastered.datastore.DataStoreHandler

class HomeViewModel : ViewModel() {

    fun getPreferences() = DataStoreHandler.getPreference()

    suspend fun clear() {
        DataStoreHandler.clear()
    }

}