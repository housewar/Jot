package com.housewar.jot.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.housewar.jot.domain.model.Note

@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var Instance: NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase{
            return Instance ?: synchronized(this){
                Room.databaseBuilder(
                    context,
                    NoteDatabase::class.java,
                    "note_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also{ Instance = it }
            }
        }
    }
}