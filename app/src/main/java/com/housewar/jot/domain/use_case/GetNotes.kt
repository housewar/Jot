package com.housewar.jot.domain.use_case

import com.housewar.jot.domain.model.Note
import com.housewar.jot.domain.repository.NoteRepository
import com.housewar.jot.domain.util.OrderBy
import com.housewar.jot.domain.util.OrderDirection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotes(
    private val repository: NoteRepository
) {
    operator fun invoke(): Flow<List<Note>> {
        return repository.getAllNotesStream()
    }
}