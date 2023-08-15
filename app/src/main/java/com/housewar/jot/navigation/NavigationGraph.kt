package com.housewar.jot.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.housewar.jot.presentation.note_entry_edit.NoteEditDestination
import com.housewar.jot.presentation.note_entry_edit.StatefulNoteEdit
import com.housewar.jot.presentation.note_list.NoteListDestination
import com.housewar.jot.presentation.note_list.StatefulNoteList

@Composable
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = NoteListDestination.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        // Note List
        composable(route = NoteListDestination.route) {
            StatefulNoteList(
                navToNoteEntry = {
                    navController.navigate(NoteEditDestination.route)
                },
                navToNoteEdit = {
                    navController.navigate("${NoteEditDestination.route}/${it}")
                },
            )
        }
        // Note Entry, i.e. Note Edit without an ID argument
        composable(route = NoteEditDestination.route) {
            StatefulNoteEdit(
                navigateUp = { navController.navigateUp() },
                navigateBack = { navController.popBackStack() }
            )
        }
        // Note Edit with an ID argument
        composable(
            route = NoteEditDestination.routeWithArgs,
            arguments = listOf(navArgument(NoteEditDestination.noteIdArg) {
                type = NavType.IntType
                defaultValue = -1
            })
        ) {
            StatefulNoteEdit(
                navigateUp = { navController.navigateUp() },
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}