package com.edwin.suburbscore

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.List
import androidx.compose.material.icons.automirrored.rounded.TrendingUp
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.edwin.suburbscore.screen.postlist.PostListScreen
import com.edwin.suburbscore.screen.createpost.CreatePostForm
import com.edwin.suburbscore.screen.dashboard.DashboardScreen
import com.edwin.suburbscore.screen.suburbrating.SuburbRatingsPage
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val (showCreatePostDialog, updateShowCreatePostDialog) = remember { mutableStateOf(false) }
        val (currentView, updateCurrentView) = remember { mutableStateOf(ViewType.POSTS) }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("CITYZEN") },
                    actions = {
                        if(currentView != ViewType.DASHBOARD) {
                            OutlinedButton(
                                onClick = {
                                    updateCurrentView(
                                        when (currentView) {
                                            ViewType.POSTS -> ViewType.RATINGS
                                            ViewType.RATINGS -> ViewType.POSTS
                                            ViewType.DASHBOARD -> ViewType.POSTS
                                        }
                                    )
                                }
                            ) {
                                Icon(
                                    imageVector = when (currentView) {
                                        ViewType.POSTS -> Icons.AutoMirrored.Rounded.TrendingUp
                                        ViewType.RATINGS -> Icons.AutoMirrored.Rounded.List
                                        ViewType.DASHBOARD -> Icons.AutoMirrored.Rounded.List
                                    },
                                    contentDescription = "Toggle View"
                                )

                                Text(
                                    text = when (currentView) {
                                        ViewType.POSTS -> "Ratings"
                                        ViewType.RATINGS -> "Posts"
                                        ViewType.DASHBOARD -> "Don't show"
                                    }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        OutlinedButton(onClick = {
                            updateCurrentView(
                                when (currentView) {
                                    ViewType.DASHBOARD -> ViewType.POSTS
                                    else -> ViewType.DASHBOARD
                                }
                            )
                        }) {
                            Text(
                                text = when (currentView) {
                                    ViewType.DASHBOARD -> "Back"
                                    else -> "Dashboard"
                                }
                            )
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { updateShowCreatePostDialog(true) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Create,
                        contentDescription = "Create"
                    )
                }
            }
        ) { innerPadding ->
            AnimatedContent(currentView) { viewState ->
                when (viewState) {
                    ViewType.POSTS -> PostListScreen(modifier = Modifier.padding(innerPadding))
                    ViewType.RATINGS -> SuburbRatingsPage(modifier = Modifier.padding(innerPadding))
                    ViewType.DASHBOARD -> DashboardScreen(modifier = Modifier.padding(innerPadding))
                }
            }

            if (showCreatePostDialog) {
                Dialog(
                    onDismissRequest = { updateShowCreatePostDialog(false) }
                ) {
                    CreatePostForm(
                        onClose = { updateShowCreatePostDialog(false) }
                    )
                }
            }
        }
    }
}

// Enum to represent the different views
enum class ViewType { POSTS, RATINGS, DASHBOARD }