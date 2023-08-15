package com.housewar.jot.presentation.note_entry_edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.housewar.jot.R
import com.housewar.jot.navigation.NavDestination
import com.housewar.jot.presentation.note_entry_edit.util.UiEvent
import com.housewar.jot.presentation.util.AppViewModelProvider
import com.housewar.jot.presentation.note_entry_edit.view_model.NoteEditViewModel
import kotlinx.coroutines.flow.collectLatest

/*
* This is the NavDestination object that is used by the NavGraph for routing
* */
object NoteEditDestination : NavDestination {
    override val route = "note_edit"
    override val titleResourceId = R.string.edit_note
    const val noteIdArg = "noteId"
    val routeWithArgs = "$route/{$noteIdArg}"
}

@Composable
fun StatefulNoteEdit(
    modifier: Modifier = Modifier,
    navigateUp: ()-> Unit = {},
    navigateBack: ()-> Unit = {}
){
    /* View Model, State, and Launched Effect are declared outside the screen composable.
     * This makes things nice and clean and allows for previews.
     */
    val viewModel: NoteEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
    // minimal state declarations to simplify the pass through to the screen composable
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest {event ->
            when(event) {
                is UiEvent.NavBackToList -> {
                    // navigateUp or navigateBack, SOP?
                    navigateBack()
                }
            }
        }
    }

    NoteEditScreen(
        uiState = uiState,
        modifier = modifier,
        canNavigateBack = true,
        navigateUp = navigateUp,
        onEvent = viewModel::onEvent
    )
}
