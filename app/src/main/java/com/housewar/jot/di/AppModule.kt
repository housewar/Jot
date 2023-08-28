package com.housewar.jot.di

import android.app.Application
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.housewar.jot.data.database.NoteDatabase
import com.housewar.jot.data.repository.NoteRepositoryImp
import com.housewar.jot.data.preferences.UserPreferencesRepository
import com.housewar.jot.domain.repository.NoteRepository
import com.housewar.jot.domain.use_case.DeleteNote
import com.housewar.jot.domain.use_case.GetNote
import com.housewar.jot.domain.use_case.GetNotes
import com.housewar.jot.domain.use_case.InsertNote
import com.housewar.jot.domain.use_case.NoteUseCases
import com.housewar.jot.domain.use_case.ReorderNotes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

private const val USER_PREFERENCES = "user_preferences"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase{
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            "note_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase) : NoteRepository {
        return NoteRepositoryImp(db.noteDao())
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(noteRepository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(noteRepository),
            getNote = GetNote(noteRepository),
            deleteNote = DeleteNote(noteRepository),
            insertNote = InsertNote(noteRepository),
            reorderNotes = ReorderNotes()
        )
    }

    @Provides
    @Singleton
    fun providePreferencesRepository(app: Application): UserPreferencesRepository {
        return UserPreferencesRepository(PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(SharedPreferencesMigration(app, USER_PREFERENCES)),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = {app.preferencesDataStoreFile(USER_PREFERENCES)}
        ))
    }
}