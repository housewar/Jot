package com.housewar.jot.presentation.note_entry_edit.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun HintTextField(
    text: String,
    placeholderText: String,
    style: TextStyle,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
){
    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = onValueChange,
            textStyle = style
        )
        if( text.isBlank() and placeholderText.isNotBlank() ){
            Text(
                text = placeholderText,
                modifier = Modifier.fillMaxWidth(),
                color = Color.DarkGray,
                style = style
            )
        }
    }
}
