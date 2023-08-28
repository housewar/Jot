package com.housewar.jot.presentation.note_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.housewar.jot.R
import com.housewar.jot.navigation.NavDestination
import com.housewar.jot.presentation.note_list.view_model.LayoutViewModel
import com.housewar.jot.presentation.note_list.view_model.NoteListViewModel

object NoteListDestination : NavDestination {
    override val route = "note_list"
    override val titleResourceId = R.string.jot
}

@Composable
fun StatefulNoteList(
    modifier: Modifier = Modifier,
    navToNoteEntry: ()-> Unit = {},
    navToNoteEdit: (Int) -> Unit = {}
) {
    val viewModel: NoteListViewModel = hiltViewModel()//viewModel(factory = AppViewModelProvider.Factory)
    val uiState by viewModel.uiState.collectAsState()
    val layoutViewModel: LayoutViewModel = hiltViewModel() //viewModel(factory = AppViewModelProvider.Factory)
    val layoutState by layoutViewModel.uiState.collectAsState()

    NoteListScreen(
        uiState = uiState,
        layoutState = layoutState,
        modifier = modifier,
        navToNoteEntry = navToNoteEntry,
        navToNoteEdit = navToNoteEdit,
        onEvent = viewModel::onEvent,
        selectLayout = layoutViewModel::switchLayout
    )
}