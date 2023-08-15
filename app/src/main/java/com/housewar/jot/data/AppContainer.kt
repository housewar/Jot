package com.housewar.jot.data

import android.content.Context
import com.housewar.jot.data.database.NoteDatabase
import com.housewar.jot.domain.repository.NoteRepository
import com.housewar.jot.data.repository.NoteRepositoryImp
import com.housewar.jot.domain.preferences.UserPreferencesRepository
import com.housewar.jot.domain.preferences.UserPreferencesRepository.Companion.getPreferencesRepository
import com.housewar.jot.domain.use_case.DeleteNote
import com.housewar.jot.domain.use_case.GetNote
import com.housewar.jot.domain.use_case.GetNotes
import com.housewar.jot.domain.use_case.InsertNote
import com.housewar.jot.domain.use_case.NoteUseCases
import com.housewar.jot.domain.use_case.ReorderNotes


interface AppContainer {
    val noteRepository : NoteRepository
    val noteUseCases: NoteUseCases
    val userPreferencesRepository: UserPreferencesRepository
}

class AppDataContainer(
    private val context: Context
    ) : AppContainer {
    override val noteRepository: NoteRepository by lazy {
        NoteRepositoryImp(NoteDatabase.getDatabase(context).noteDao())
    }
    override val noteUseCases: NoteUseCases by lazy {
        NoteUseCases(
            getNotes = GetNotes(noteRepository),
            getNote = GetNote(noteRepository),
            deleteNote = DeleteNote(noteRepository),
            insertNote = InsertNote(noteRepository),
            reorderNotes = ReorderNotes()
        )
    }
    override val userPreferencesRepository: UserPreferencesRepository by lazy{
        getPreferencesRepository(context)
    }

}
