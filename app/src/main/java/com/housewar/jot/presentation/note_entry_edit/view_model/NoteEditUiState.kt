package com.housewar.jot.presentation.note_entry_edit.view_model

import com.housewar.jot.domain.model.Note

data class NoteEditUiState(
    val note: Note = Note(),
    val title: NoteTextFieldState = NoteTextFieldState(),
    val body: NoteTextFieldState = NoteTextFieldState(),
)

fun NoteEditUiState.toNote() : Note = Note(
    id = note.id,
    title = title.text,
    body = body.text,
    timeStamp = note.timeStamp
)

fun Note.toNoteEditUiState() : NoteEditUiState = NoteEditUiState(
    note = this,
    title = NoteTextFieldState(
        text = title,
        showHint = title.isBlank()
    ),
    body = NoteTextFieldState(
        text = body,
        showHint = body.isBlank()
    )
)