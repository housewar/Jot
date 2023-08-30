package com.housewar.jot.presentation.note_entry_edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.housewar.jot.R
import com.housewar.jot.data.SampleNoteListProvider
import com.housewar.jot.domain.model.Note
import com.housewar.jot.presentation.JotTopBar
import com.housewar.jot.presentation.note_entry_edit.components.HintTextField
import com.housewar.jot.presentation.note_entry_edit.util.NoteEditEvent
import com.housewar.jot.presentation.note_entry_edit.view_model.NoteEditUiState
import com.housewar.jot.presentation.note_entry_edit.view_model.toNoteEditUiState

@Composable
fun NoteEditScreen(
    uiState: NoteEditUiState,
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean = true,
    navigateUp: ()-> Unit = {},
    onEvent: (NoteEditEvent) -> Unit = {}
){
    Scaffold(
        modifier = modifier.padding(16.dp),
        topBar = {
            JotTopBar(
                title = "",
                canNavigateBack = canNavigateBack,
                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
                navigateUp = { onEvent(NoteEditEvent.SaveNote) }
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            HintTextField(
                text = uiState.title,
                placeholderText = stringResource(R.string.title),
                onValueChange = { onEvent(NoteEditEvent.UpdateTitle(it)) },
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            HintTextField(
                text = uiState.body,
                placeholderText = stringResource(R.string.note),
                onValueChange = { onEvent(NoteEditEvent.UpdateBody(it)) },
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteEditScreenPreview(){
    val note: Note = SampleNoteListProvider.getNoteList().first()
    val uiState = note.toNoteEditUiState()
    NoteEditScreen(
        uiState = uiState
    )
}