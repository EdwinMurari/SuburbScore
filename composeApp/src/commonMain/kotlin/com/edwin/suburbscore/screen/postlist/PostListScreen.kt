package com.edwin.suburbscore.screen.postlist

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PostListScreen(
    modifier: Modifier = Modifier,
    viewModel: PostListViewModel = viewModel { PostListViewModel() },
) {
    val uiState by viewModel.uiState.collectAsState()

    PostListUi(
        modifier = modifier,
        uiState = uiState,
        onCategorySelect = viewModel::onCategorySelect,
        onSuburbSelect = viewModel::onSuburbSelect
    )
}

@Composable
fun PostListUi(
    modifier: Modifier = Modifier,
    uiState: PostListUiState,
    onCategorySelect: (String) -> Unit,
    onSuburbSelect: (String) -> Unit
) {
    AnimatedContent(uiState) { targetState ->
        when (targetState) {
            PostListUiState.Error -> {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Something went wrong", modifier = Modifier.padding(16.dp))
                }
            }

            PostListUiState.Loading -> {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is PostListUiState.Success -> {
                PostListContent(
                    modifier = modifier,
                    uiState = targetState,
                    onCategorySelect = onCategorySelect,
                    onSuburbSelect = onSuburbSelect
                )
            }
        }
    }
}

@Composable
private fun PostListContent(
    modifier: Modifier = Modifier,
    uiState: PostListUiState.Success,
    onCategorySelect: (String) -> Unit,
    onSuburbSelect: (String) -> Unit
) {
    Row(
        modifier = modifier.padding(16.dp)
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
    }
}