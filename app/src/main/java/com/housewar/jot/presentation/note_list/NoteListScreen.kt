@file:OptIn(ExperimentalMaterial3Api::class)

package com.housewar.jot.presentation.note_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.housewar.jot.R
import com.housewar.jot.data.SampleNoteListProvider
import com.housewar.jot.domain.model.Note
import com.housewar.jot.presentation.JotTopBar
import com.housewar.jot.presentation.note_list.components.NoteCard
import com.housewar.jot.presentation.note_list.util.NoteListEvent
import com.housewar.jot.presentation.note_list.view_model.LayoutUiState
import com.housewar.jot.presentation.note_list.view_model.NoteListUiState

@Composable
fun NoteListScreen(
    uiState: NoteListUiState,
    layoutState: LayoutUiState,
    modifier: Modifier = Modifier,
    navToNoteEntry: () -> Unit = {},
    navToNoteEdit: (Int) -> Unit = {},
    onEvent: (NoteListEvent) -> Unit = {},
    selectLayout: (Boolean) -> Unit = {}
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier,
        topBar = {
            JotTopBar(
                title = stringResource(id = NoteListDestination.titleResourceId),
                canNavigateBack = false,
                actions = {
                    IconButton(
                        onClick = {selectLayout(!layoutState.isLinearLayout)}
                    ) {
                        Icon(
                            painter = painterResource(layoutState.toggleIcon),
                            contentDescription = stringResource(layoutState.toggleContentDescription),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )

        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navToNoteEntry,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            if ( uiState.notes.isEmpty() ) {
                NoteCard(
                    note = Note(
                        title = stringResource(R.string.jot_down_notes),
                        body = stringResource(R.string.press_the_plus_button),
                    ),
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                if( layoutState.isLinearLayout ){
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(uiState.notes) { note ->
                            NoteCard(
                                note = note,
                                modifier = Modifier.clickable(enabled = true) {
                                    navToNoteEdit(note.id)
                                },
                                deleteNote = { onEvent(NoteListEvent.DeleteNote(note)) },
                                maxLines = 2
                            )
                        }
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(uiState.notes) { note ->
                            NoteCard(
                                note = note,
                                modifier = Modifier.clickable(enabled = true) {
                                    navToNoteEdit(note.id)
                                },
                                deleteNote = { onEvent(NoteListEvent.DeleteNote(note)) },
                                maxLines = 4
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NoteLinearListScreenPreview() {
    NoteListScreen(
        uiState = NoteListUiState(SampleNoteListProvider.getNoteList()),
        layoutState = LayoutUiState()
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NoteGridScreenPreview() {
    NoteListScreen(
        uiState = NoteListUiState(
            notes = SampleNoteListProvider.getNoteList()
        ),
        layoutState = LayoutUiState(
            isLinearLayout = false
        )
    )
}