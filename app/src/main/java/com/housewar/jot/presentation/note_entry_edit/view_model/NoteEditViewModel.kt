package com.housewar.jot.presentation.note_entry_edit.view_model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.housewar.jot.domain.model.InvalidNoteException
import com.housewar.jot.domain.use_case.NoteUseCases
import com.housewar.jot.presentation.note_entry_edit.NoteEditDestination
import com.housewar.jot.presentation.note_entry_edit.util.NoteEditEvent
import com.housewar.jot.presentation.note_entry_edit.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteEditViewModel @Inject constructor(
    ssHandle: SavedStateHandle,
    private val noteUseCases: NoteUseCases
) : ViewModel() {
    // get the noteID argument, if it's empty, pass 0
    private val noteId: Int = ssHandle[NoteEditDestination.noteIdArg] ?: -1

    private val _uiState: MutableStateFlow<NoteEditUiState> = MutableStateFlow(NoteEditUiState())
    val uiState: StateFlow<NoteEditUiState> = _uiState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    // if the note is ever re-queried, the prior job should be canceled and reassigned.
    private var getNoteJob: Job? = null

    // if the noteID argument is not -1, replace the empty note with the actual note
    init {
        if (noteId != -1) {
            getNoteJob = noteUseCases.getNote(noteId).filterNotNull().map { note ->
                _uiState.update {
                    note.toNoteEditUiState()
                }
            }.launchIn(viewModelScope)
        }
    }

    fun onEvent(event: NoteEditEvent) {
        when (event) {
            NoteEditEvent.SaveNote ->
                viewModelScope.launch {
                    try {
                        noteUseCases.insertNote(_uiState.value.toNote())
                        _eventFlow.emit(UiEvent.NavBackToList)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(UiEvent.NavBackToList)
                    }
                }

            is NoteEditEvent.UpdateBody -> {
                _uiState.update {
                    it.copy(
                        body = event.text
                    )
                }
            }

            is NoteEditEvent.UpdateTitle -> {
                _uiState.update {
                    it.copy(
                        title = event.text
                    )
                }
            }
        }
    }
}

