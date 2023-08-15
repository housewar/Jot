package com.housewar.jot.presentation.note_list.util

import com.housewar.jot.domain.model.Note
import com.housewar.jot.domain.util.OrderBy

sealed class NoteListEvent {
    data class Order(val orderBy: OrderBy): NoteListEvent()
    data class DeleteNote(val note: Note): NoteListEvent()
    object RestoreNote: NoteListEvent()
}