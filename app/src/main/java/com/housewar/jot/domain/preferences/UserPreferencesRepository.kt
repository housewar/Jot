package com.housewar.jot.domain.preferences

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val USER_PREFERENCES = "user_preferences"

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
){
    companion object {

        const val TAG = "UserPreferencesRepo"

        val IS_LINEAR_LAYOUT = booleanPreferencesKey("is_linear_layout")

        fun getPreferencesRepository(context: Context): UserPreferencesRepository {
            return UserPreferencesRepository(PreferenceDataStoreFactory.create(
                corruptionHandler = ReplaceFileCorruptionHandler(
                    produceNewData = { emptyPreferences() }
                ),
                migrations = listOf(SharedPreferencesMigration(context, USER_PREFERENCES)),
                scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
                produceFile = {context.preferencesDataStoreFile(USER_PREFERENCES)}
                ))
        }
/*
        fun getPreferencesRepository(context: Context): UserPreferencesRepository {
            return UserPreferencesRepository(context.dataStore)
        }
 */
    }

    val isLinearLayout: Flow<Boolean> = dataStore.data
        .catch{
            if (it is IOException){
                Log.e(TAG,"Error reading preferences",it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[IS_LINEAR_LAYOUT] ?: true
        }

    suspend fun saveLayoutPreference(isLinearLayout: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_LINEAR_LAYOUT] = isLinearLayout
        }
    }
}