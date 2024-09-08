package com.edwin.suburbscore.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edwin.suburbscore.model.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi
import suburbscore.composeapp.generated.resources.Res

@OptIn(ExperimentalResourceApi::class)
class PostListViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<PostListUiState>(PostListUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private lateinit var postList: List<Post>

    init {
        viewModelScope.launch {
            val byteArray = Res.readBytes("files/posts.json").decodeToString()

            postList = Json.decodeFromString<List<Post>>(byteArray)

            val categoryList = postList.map { it.category }.distinct()
            val suburbList = postList.map { it.suburb }.distinct()

            _uiState.value = PostListUiState.Success(
                postList = postList,
                categories = categoryList,
                selectedCategories = setOf(),
                suburbs = suburbList,
                selectedSuburb = null
            )
        }
    }

    fun onCategorySelect(filter: String) {
        _uiState.value = (_uiState.value as? PostListUiState.Success)?.let { currentState ->
            val newFilters = currentState.selectedCategories.toMutableSet().apply { // Use Set
                if (contains(filter)) {
                    remove(filter)
                } else {
                    add(filter)
                }
            }

            currentState.copy(
                postList = if (newFilters.isEmpty()) {
                    postList // Show all posts if no filters are selected
                } else {
                    postList.filter { post ->
                        newFilters.contains(post.category) // Directly check without mapping
                    }
                },
                selectedCategories = newFilters
            )
        } ?: _uiState.value // Preserve state if not Success
    }

    fun onSuburbSelect(suburb: String) {
        _uiState.value = (_uiState.value as? PostListUiState.Success)?.let { currentState ->
            currentState.copy(
                postList = if (suburb.isBlank()) {
                    postList // Show all posts if suburb is blank
                } else {
                    postList.filter { post ->
                        post.suburb.equals(suburb, ignoreCase = true)
                    }
                },
                selectedSuburb = suburb
            )
        } ?: _uiState.value // Preserve state if not Success
    }
}