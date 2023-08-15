package com.housewar.jot.presentation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.housewar.jot.R
import com.housewar.jot.navigation.NavigationGraph
import com.housewar.jot.presentation.note_list.NoteListDestination

@Composable
fun JotApp(
    navController: NavHostController = rememberNavController(),
    startDestination: String = NoteListDestination.route
){
    NavigationGraph(
        navController = navController,
        startDestination = startDestination
    )
}

@Composable
fun JotTopBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    actions: @Composable() (RowScope.()-> Unit) = {},
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {}
){
    CenterAlignedTopAppBar(
        title = { Text(title) },
        modifier = modifier,
        actions = actions,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}