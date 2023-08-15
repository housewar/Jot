package com.housewar.jot.presentation.note_entry_edit.util

import com.housewar.jot.domain.model.Note

sealed class NoteEditEvent{
    data class UpdateTitle(val text: String): NoteEditEvent()
    data class UpdateBody(val text: String): NoteEditEvent()
    object DeleteNote: NoteEditEvent()
    object RestoreNote: NoteEditEvent()
    object SaveNote: NoteEditEvent()
}
