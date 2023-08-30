package com.housewar.jot.presentation.note_entry_edit.view_model

import com.housewar.jot.domain.model.Note
import java.util.Date

data class NoteEditUiState(
    val id: Int = 0,
    val title: String = "",
    val body: String = "",
    val timeStamp: Long = Date().time
)

fun NoteEditUiState.toNote() : Note = Note(
    id = this.id,
    title = this.title,
    body = this.body,
    timeStamp = this.timeStamp
)

fun Note.toNoteEditUiState() : NoteEditUiState = NoteEditUiState(
    id = this.id,
    title = this.title,
    body = this.body,
    timeStamp = this.timeStamp
)