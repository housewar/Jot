package com.housewar.jot.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class Note (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String = "",
    val body: String = "",
    val timeStamp: Long = 0
)

class InvalidNoteException(message: String): Exception(message)