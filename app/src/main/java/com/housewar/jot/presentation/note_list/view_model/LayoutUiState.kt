package com.housewar.jot.presentation.note_list.view_model

import com.housewar.jot.R
import com.housewar.jot.data.preferences.util.ListLayout

data class LayoutUiState (
    val listLayout: ListLayout = ListLayout.LINEAR,
    val toggleContentDescription: Int =
        when (listLayout){
            ListLayout.LINEAR -> R.string.grid_layout
            ListLayout.GRID -> R.string.linear_layout
        },
    val toggleIcon: Int =
        when (listLayout){
            ListLayout.LINEAR -> R.drawable.ic_grid_layout
            ListLayout.GRID -> R.drawable.ic_linear_layout
        }
)