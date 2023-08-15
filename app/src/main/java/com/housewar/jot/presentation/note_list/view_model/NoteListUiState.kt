package com.housewar.jot.presentation.note_list.view_model

import com.housewar.jot.domain.model.Note
import com.housewar.jot.domain.util.OrderBy
import com.housewar.jot.domain.util.OrderDirection

data class NoteListUiState (
    val notes: List<Note> = listOf(),
    val orderBy: OrderBy = OrderBy.Timestamp(OrderDirection.Descending)
)