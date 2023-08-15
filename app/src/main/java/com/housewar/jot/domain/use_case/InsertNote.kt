package com.housewar.jot.domain.use_case

import com.housewar.jot.domain.model.InvalidNoteException
import com.housewar.jot.domain.model.Note
import com.housewar.jot.domain.repository.NoteRepository
import kotlin.jvm.Throws

class InsertNote(
    private val repository: NoteRepository
) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note){
        if(note.title.isBlank()){
            throw (InvalidNoteException("Note title cannot be empty"))
        } else {
            repository.insertNote(note)
        }
    }
}