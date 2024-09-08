package com.edwin.suburbscore.screen.postlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.edwin.suburbscore.component.PostItem

@Composable
fun PostList(
    modifier: Modifier = Modifier,
    uiState: PostListUiState.Success
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(uiState.postList, key = { it.id }) { item ->
            PostItem(
                post = item,
                onPostClick = {}
            )
        }
    }
}