package com.housewar.jot.domain.use_case

import com.housewar.jot.domain.model.Note
import com.housewar.jot.domain.repository.NoteRepository

class DeleteNote(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note){
        repository.deleteNote(note)
    }
}