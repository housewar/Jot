package com.housewar.jot

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.housewar.jot.data.AppContainer
import com.housewar.jot.data.AppDataContainer
import com.housewar.jot.domain.preferences.UserPreferencesRepository

/*
private const val LAYOUT_PREFERENCE_NAME = "layout_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = LAYOUT_PREFERENCE_NAME
)
*/

class JotApplication: Application() {
    lateinit var container: AppContainer

    lateinit var userPreferencesRepository: UserPreferencesRepository

        override fun onCreate() {
            super.onCreate()
            container = AppDataContainer(this)
            //userPreferencesRepository = UserPreferencesRepository(dataStore)
        }
}