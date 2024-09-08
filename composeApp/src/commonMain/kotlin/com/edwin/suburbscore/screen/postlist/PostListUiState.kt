package com.edwin.suburbscore.screen.postlist

import com.edwin.suburbscore.model.Post

sealed interface PostListUiState {

    data class Success(
        val postList: List<Post>,
        val selectedCategories: Set<String>,
        val categories: List<String>,
        val suburbs: List<String>,
        val selectedSuburb: String?
    ) : PostListUiState

    data object Error : PostListUiState
    data object Loading : PostListUiState
}