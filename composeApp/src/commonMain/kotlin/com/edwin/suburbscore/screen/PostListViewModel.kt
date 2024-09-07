package com.edwin.suburbscore.screen

import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edwin.suburbscore.model.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi
import suburbscore.composeapp.generated.resources.Res

@OptIn(ExperimentalResourceApi::class)
class PostListViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<PostListUiState>(PostListUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private lateinit var postList: List<Post>
    private val filterList = listOf("Affordable housing", "Traffic", "Crime", "Amenities")

    init {
        viewModelScope.launch {
            val byteArray = Res.readBytes("files/posts.json").decodeToString()
            postList = Json.decodeFromString<List<Post>>(byteArray)
            _uiState.value = PostListUiState.Success(
                postList = postList,
                filters = filterList,
                selectedFilters = listOf()
            )
        }
    }

    fun onFilterSelect(filter: String) {
        _uiState.update { currentState ->
            (currentState as? PostListUiState.Success)?.let {
                val newFilters = currentState.selectedFilters.toMutableList().apply {
                    if (contains(filter)) {
                        remove(filter)
                    } else {
                        add(filter)
                    }
                }

                currentState.copy(
                    postList = postList.filter { post ->
                        if (newFilters.isEmpty()) true else newFilters.map { it.lowercase() }.contains(post.category.lowercase())
                    },
                    selectedFilters = newFilters
                )
            } ?: currentState
        }
    }
}