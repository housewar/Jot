package com.housewar.jot.domain.use_case

import com.housewar.jot.domain.model.Note
import com.housewar.jot.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class GetNote(
    private val repository: NoteRepository
) {
    operator fun invoke(
        id: Int
    ) : Flow<Note> {
        return repository.getNoteStream(id)
    }
}