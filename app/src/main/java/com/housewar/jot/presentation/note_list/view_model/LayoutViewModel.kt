package com.housewar.jot.presentation.note_list.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.housewar.jot.data.preferences.UserPreferencesRepository
import com.housewar.jot.data.preferences.util.ListLayout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LayoutViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val userPreferencesFlow = userPreferencesRepository.userPreferencesFlow

    val uiState: StateFlow<LayoutUiState> =
        userPreferencesRepository.userPreferencesFlow.map { userPref ->
            LayoutUiState(
                listLayout = userPref.listLayout
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = LayoutUiState()
        )
    /*
     * [selectLayout] change the layout and icons accordingly and
     * save the selection in DataStore through [userPreferencesRepository]
     */
    fun switchLayout() {
        viewModelScope.launch {
            userPreferencesRepository.setListLayout(
                if (uiState.value.listLayout ==  ListLayout.LINEAR) {
                    ListLayout.GRID
                } else {
                    ListLayout.LINEAR
                }
            )
        }
    }
}