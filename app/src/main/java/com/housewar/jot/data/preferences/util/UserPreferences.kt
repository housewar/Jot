package com.housewar.jot.data.preferences.util

data class UserPreferences(
    val listLayout: ListLayout,
    val isLinearLayout: Boolean
)

enum class ListLayout {
    LINEAR,
    GRID
}
