package com.housewar.jot.presentation.util

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.housewar.jot.JotApplication
import com.housewar.jot.presentation.note_entry_edit.view_model.NoteEditViewModel
import com.housewar.jot.presentation.note_list.view_model.LayoutViewModel
import com.housewar.jot.presentation.note_list.view_model.NoteListViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            NoteListViewModel(
                jotApplication().container.noteUseCases
            )
        }
        initializer {
            NoteEditViewModel(
                this.createSavedStateHandle(),
                jotApplication().container.noteUseCases
            )
        }
        initializer {
            LayoutViewModel(
                jotApplication().container.userPreferencesRepository
            )
        }
    }
}

fun CreationExtras.jotApplication(): JotApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as JotApplication)