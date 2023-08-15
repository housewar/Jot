package com.housewar.jot.presentation.note_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.housewar.jot.R
import com.housewar.jot.domain.model.Note

@Composable
fun NoteCard(
    note: Note,
    modifier: Modifier = Modifier,
    deleteNote: (Note) -> Unit = {},
    maxLines: Int = 1
){
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(4.dp)
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
            Text(text = note.title, fontWeight = FontWeight.Bold ,style = MaterialTheme.typography.titleMedium)
            Spacer(modifier.height(4.dp))
            if (note.body != ""){
                Text(text = note.body, style = MaterialTheme.typography.bodyMedium, maxLines = maxLines, overflow = TextOverflow.Ellipsis)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = { deleteNote(note) },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = stringResource(R.string.delete_note)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteCardPreview(){
    NoteCard(
        note = Note(
            id = 0,
            title = "This is my Note",
            body = "There are many like it, but this one is mine.",
            timeStamp = 0
        )
    )
}