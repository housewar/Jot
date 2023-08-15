package com.housewar.jot.domain.use_case

import com.housewar.jot.domain.model.Note
import com.housewar.jot.domain.util.OrderBy
import com.housewar.jot.domain.util.OrderDirection

class ReorderNotes() {
    operator fun invoke(
        notes: List<Note>,
        orderBy: OrderBy
    ): List<Note> {
        return when (orderBy.orderDirection) {
            is OrderDirection.Ascending ->
                when (orderBy) {
                    is OrderBy.Timestamp -> notes.sortedBy { it.timeStamp }
                    is OrderBy.Title -> notes.sortedBy { it.title.lowercase() }
                }
            is OrderDirection.Descending ->
                when (orderBy) {
                    is OrderBy.Timestamp -> notes.sortedByDescending { it.timeStamp }
                    is OrderBy.Title -> notes.sortedByDescending { it.title.lowercase() }
                }
        }
    }
}