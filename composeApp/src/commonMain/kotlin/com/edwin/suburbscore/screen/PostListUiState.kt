package com.edwin.suburbscore.screen

import com.edwin.suburbscore.model.Post

sealed interface PostListUiState {

    data class Success(
        val postList: List<Post>,
        val selectedFilters: List<String>,
        val filters: List<String>
    ) : PostListUiState

    data object Error : PostListUiState
    data object Loading : PostListUiState
}