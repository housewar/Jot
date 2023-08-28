package com.housewar.jot.domain.repository

import com.housewar.jot.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNoteStream(id: Int): Flow<Note>

    fun getAllNotesStream(): Flow<List<Note>>

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun updateNote(note: Note)
}