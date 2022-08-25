package com.example.test6remastered.datastore

import android.app.Application
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.test6remastered.app.App
import kotlinx.coroutines.flow.Flow

object DataStoreHandler {

    private val Application.dataStore by preferencesDataStore(name = "userSession")

    fun getPreference(): Flow<androidx.datastore.preferences.core.Preferences> {
        return App.appContext.dataStore.data
    }

    suspend fun savePreference(key: String, sessionValue: Boolean, tokenValue: String) {
        App.appContext.dataStore.edit {
            it[booleanPreferencesKey(key)] = sessionValue
            it[stringPreferencesKey(key)] = tokenValue
        }
    }

    suspend fun clear() {
        App.appContext.dataStore.edit {
            it.clear()
        }
    }



}