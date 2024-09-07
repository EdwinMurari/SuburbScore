package com.edwin.suburbscore.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.edwin.suburbscore.component.PostItem

@Composable
fun PostListScreen(
    viewModel: PostListViewModel = viewModel { PostListViewModel() },
) {
    val uiState by viewModel.uiState.collectAsState()

    PostListUi(uiState)
}

@Composable
fun PostListUi(uiState: PostListUiState) {
    AnimatedContent(uiState) { targetState ->
        when (targetState) {
            PostListUiState.Error -> {
                Text("Something went wrong")
            }

            PostListUiState.Loading -> {
                CircularProgressIndicator()
            }

            is PostListUiState.Success -> {
                PostListSuccess(targetState)
            }
        }
    }
}

@Composable
fun PostListSuccess(uiState: PostListUiState.Success) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = "Create"
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(uiState.postList, key = { it.id }) { item ->
                PostItem(item)
            }
        }
    }
}
