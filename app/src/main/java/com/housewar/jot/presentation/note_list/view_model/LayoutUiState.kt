package com.housewar.jot.presentation.note_list.view_model

import com.housewar.jot.R

data class LayoutUiState (
    val isLinearLayout: Boolean = true,
    val toggleContentDescription: Int =
        if (isLinearLayout) R.string.grid_layout else R.string.linear_layout,
    val toggleIcon: Int =
        if (isLinearLayout) R.drawable.ic_grid_layout else R.drawable.ic_linear_layout
)