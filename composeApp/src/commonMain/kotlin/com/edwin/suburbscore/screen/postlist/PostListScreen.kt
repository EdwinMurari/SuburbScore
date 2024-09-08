package com.edwin.suburbscore.screen.postlist

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.List
import androidx.compose.material.icons.automirrored.rounded.TrendingUp
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.edwin.suburbscore.screen.postlist.createpost.CreatePostForm
import com.edwin.suburbscore.screen.suburbrating.SuburbRatingsPage

@Composable
fun PostListScreen(
    viewModel: PostListViewModel = viewModel { PostListViewModel() },
) {
    val uiState by viewModel.uiState.collectAsState()

    PostListUi(
        uiState = uiState,
        onCategorySelect = viewModel::onCategorySelect,
        onSuburbSelect = viewModel::onSuburbSelect
    )
}

@Composable
fun PostListUi(
    uiState: PostListUiState,
    onCategorySelect: (String) -> Unit,
    onSuburbSelect: (String) -> Unit
) {
    AnimatedContent(uiState) { targetState ->
        when (targetState) {
            PostListUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Something went wrong", modifier = Modifier.padding(16.dp))
                }
            }

            PostListUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is PostListUiState.Success -> {
                PostListSuccess(
                    uiState = targetState,
                    onCategorySelect = onCategorySelect,
                    onSuburbSelect = onSuburbSelect
                )
            }
        }
    }
}

@Composable
fun PostListSuccess(
    uiState: PostListUiState.Success,
    onCategorySelect: (String) -> Unit,
    onSuburbSelect: (String) -> Unit
) {
    val (showCreatePostDialog, updateShowCreatePostDialog) = remember { mutableStateOf(false) }
    val (currentView, updateCurrentView) = remember { mutableStateOf(ViewType.POSTS) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Victoria Suburb Posts") },
                actions = {
                    Button(
                        onClick = {
                            updateCurrentView(
                                when (currentView) {
                                    ViewType.POSTS -> ViewType.RATINGS
                                    ViewType.RATINGS -> ViewType.POSTS
                                }
                            )
                        }
                    ) {
                        Icon(
                            imageVector = when (currentView) {
                                ViewType.POSTS -> Icons.AutoMirrored.Rounded.TrendingUp
                                ViewType.RATINGS -> Icons.AutoMirrored.Rounded.List
                            },
                            contentDescription = "Toggle View"
                        )

                        Text(
                            text = when (currentView) {
                                ViewType.POSTS -> "Ratings"
                                ViewType.RATINGS -> "Posts"
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
                ViewType.POSTS -> PostListContent(
                    innerPadding = innerPadding,
                    uiState = uiState,
                    onCategorySelect = onCategorySelect,
                    onSuburbSelect = onSuburbSelect,
                    showCreatePostDialog = showCreatePostDialog,
                    updateShowCreatePostDialog = updateShowCreatePostDialog
                )

                ViewType.RATINGS -> SuburbRatingsPage()
            }
        }

    }
}

@Composable
private fun PostListContent(
    innerPadding: PaddingValues,
    uiState: PostListUiState.Success,
    onCategorySelect: (String) -> Unit,
    onSuburbSelect: (String) -> Unit,
    showCreatePostDialog: Boolean,
    updateShowCreatePostDialog: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(innerPadding)
            .padding(16.dp)
    ) {
        FiltersSection(
            modifier = Modifier.width(250.dp),
            categories = uiState.categories,
            suburbs = uiState.suburbs,
            selectedCategories = uiState.selectedCategories,
            selectedSuburb = uiState.selectedSuburb,
            onCategorySelect = onCategorySelect,
            onSuburbSelect = onSuburbSelect
        )

        Spacer(modifier = Modifier.width(16.dp))

        PostList(
            modifier = Modifier.weight(1f),
            uiState = uiState
        )

        if (showCreatePostDialog) {
            Dialog(
                onDismissRequest = { updateShowCreatePostDialog(false) }
            ) {
                CreatePostForm(
                    categories = uiState.categories,
                    suburbs = uiState.suburbs,
                    onClose = { updateShowCreatePostDialog(false) },
                    onSave = {}
                )
            }
        }
    }
}

// Enum to represent the different views
enum class ViewType { POSTS, RATINGS }