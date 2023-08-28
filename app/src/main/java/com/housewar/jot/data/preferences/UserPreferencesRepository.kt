package com.housewar.jot.data.preferences

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.housewar.jot.data.preferences.util.ListLayout
import com.housewar.jot.data.preferences.util.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
){
    // Object containing all preference keys/types
    private companion object PreferencesKeys {
        const val TAG = "UserPreferencesRepo"
        val LIST_LAYOUT = stringPreferencesKey("list_layout")
        val IS_LINEAR_LAYOUT = booleanPreferencesKey("is_linear_layout")
        // !!! Additional Keys go here !!!
    }

    // Maps the datastore data to a UserPreferences object that's easier to read
    private fun mapUserPreferences(preferences: Preferences): UserPreferences {
        val listLayout = ListLayout.valueOf(
            preferences[PreferencesKeys.LIST_LAYOUT] ?: ListLayout.LINEAR.name
        )
        val isLinearLayout = preferences[PreferencesKeys.IS_LINEAR_LAYOUT] ?: true

        // !!! Additional Keys go here !!!
        return UserPreferences(
            listLayout = listLayout,
            isLinearLayout = isLinearLayout
            // !!! Additional Keys go here !!!
        )
    }

    /**
     * Get the user preferences flow.
     */
    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        // catch file IO exceptions
        .catch {exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences.", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {preferences ->
            // map the dataStore data to a UserPreferences data class object
            mapUserPreferences(preferences)
        }

    suspend fun setListLayout( listLayout: ListLayout ){
        dataStore.edit {preferences ->
            preferences[LIST_LAYOUT] = listLayout.name
        }
    }

    suspend fun fetchInitialPreferences() =
        mapUserPreferences(dataStore.data.first().toPreferences())
}