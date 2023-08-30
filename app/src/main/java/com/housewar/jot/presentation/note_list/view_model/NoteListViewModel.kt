package com.housewar.jot.presentation.note_list.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.housewar.jot.domain.model.Note
import com.housewar.jot.domain.use_case.NoteUseCases
import com.housewar.jot.presentation.note_list.util.NoteListEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    val notes: StateFlow<List<Note>> =
        noteUseCases.getNotes().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = listOf()
        )

    private var deletedNote: Note? = null

    fun onEvent(event: NoteListEvent) {
        when (event) {
            is NoteListEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNote(event.note)
                    deletedNote = event.note
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