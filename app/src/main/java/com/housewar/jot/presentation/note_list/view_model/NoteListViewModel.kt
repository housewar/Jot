package com.housewar.jot.presentation.note_list.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.housewar.jot.domain.model.Note
import com.housewar.jot.domain.preferences.UserPreferencesRepository
import com.housewar.jot.domain.use_case.NoteUseCases
import com.housewar.jot.domain.util.OrderBy
import com.housewar.jot.presentation.note_list.util.NoteListEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NoteListViewModel(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private val _uiState: MutableStateFlow<NoteListUiState> = MutableStateFlow(NoteListUiState())
    val uiState: StateFlow<NoteListUiState> = _uiState.asStateFlow()

    private var getNotesJob: Job? = null

    init {
        getNotesJob = noteUseCases.getNotes().map { notes ->
            _uiState.update { state ->
                state.copy(
                    notes = notes
                )
            }
        }.launchIn(viewModelScope)
    }

    private var deletedNote: Note? = null

    private fun reorderNotes(orderBy: OrderBy) {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    notes = noteUseCases.reorderNotes(notes = state.notes, orderBy = orderBy),
                    orderBy = orderBy
                )
            }
        }
    }

    fun onEvent(event: NoteListEvent) {
        when (event) {
            is NoteListEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNote(event.note)
                    deletedNote = event.note
                }
            }

            is NoteListEvent.Order -> {
                // Check class equality, not referential equality
                if (uiState.value.orderBy::class != event.orderBy::class ||
                    uiState.value.orderBy.orderDirection != event.orderBy.orderDirection
                ) {
                    viewModelScope.launch {
                        reorderNotes(event.orderBy)
                    }
                }
            }

            NoteListEvent.RestoreNote -> {
                viewModelScope.launch {
                    deletedNote?.let { noteUseCases.insertNote(it) }
                    deletedNote = null
                }
            }
        }
    }
}