package com.housewar.jot.data.repository

import com.housewar.jot.domain.model.Note
import com.housewar.jot.data.database.NoteDao
import com.housewar.jot.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImp(
    private val noteDao: NoteDao
) : NoteRepository {
    override fun getNoteStream(id: Int): Flow<Note> = noteDao.getNote(id)

    override fun getAllNotesStream(): Flow<List<Note>> = noteDao.getAllNotes()

    override suspend fun insertNote(note: Note) = noteDao.insert(note)

    override suspend fun deleteNote(note: Note) = noteDao.delete(note)

    override suspend fun updateNote(note: Note) = noteDao.update(note)

}