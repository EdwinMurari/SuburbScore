package com.edwin.suburbscore.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

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
                Text("Something went wrong")
            }

            PostListUiState.Loading -> {
                CircularProgressIndicator()
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

@OptIn(ExperimentalMaterialApi::class, ExperimentalLayoutApi::class)
@Composable
fun PostListSuccess(
    uiState: PostListUiState.Success,
    onCategorySelect: (String) -> Unit,
    onSuburbSelect: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(content = {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    uiState.categories.forEach { filter ->
                        FilterChip(
                            selected = uiState.selectedCategories
                                .map { it.lowercase() }
                                .contains(filter),
                            onClick = { onCategorySelect(filter) },
                            content = { Text(text = filter) }
                        )
                    }
                }
            })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = "Create"
                )
            }
        }
    ) { paddingValues ->
        Row(
            modifier = Modifier.padding(
                start = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                end = paddingValues.calculateEndPadding(LocalLayoutDirection.current)
            )
        ) {
            FiltersSection(
                modifier = Modifier.width(200.dp),
                categories = uiState.categories,
                suburbs = uiState.suburbs,
                selectedCategories = uiState.selectedCategories,
                selectedSuburb = uiState.selectedSuburb,
                onCategorySelect = onCategorySelect,
                onSuburbSelect = onSuburbSelect
            )

            PostList(
                modifier = Modifier.weight(1f),
                paddingValues = paddingValues,
                uiState = uiState
            )
        }
    }
}