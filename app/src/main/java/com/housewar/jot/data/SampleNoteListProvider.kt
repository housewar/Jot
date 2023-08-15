package com.housewar.jot.data

import com.housewar.jot.domain.model.Note

object SampleNoteListProvider {
    private val notes: MutableList<Note> = mutableListOf(
        Note(
            id = 0,
            title = "Jot down notes!",
            body = "Press the + button at the bottom of the screen to create a new note.",
            timeStamp = 0
        ),
        Note(
            id = 0,
            title = "Quick and easy!",
            body = "Shopping lists. Things to remember. Whatever you like.",
            timeStamp = 0
        ))

    fun getNoteList(): List<Note> {
        return notes
    }
}