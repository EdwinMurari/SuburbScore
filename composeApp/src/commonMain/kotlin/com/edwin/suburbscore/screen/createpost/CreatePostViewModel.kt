package com.edwin.suburbscore.screen.createpost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edwin.suburbscore.model.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi
import suburbscore.composeapp.generated.resources.Res

class CreatePostViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<CreatePostUiState>(CreatePostUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            loadPosts("files/posts.json")
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    private suspend fun loadPosts(filePath: String) {
        try {
            val jsonString = Res.readBytes(filePath).decodeToString()
            val postList = Json.decodeFromString<List<Post>>(jsonString)
            val categoryList = postList.map { it.category }.distinct()
            val suburbList = postList.map { it.suburb }.distinct()

            _uiState.value = CreatePostUiState.Success(suburbList, categoryList)
        } catch (e: Exception) {
            _uiState.value = CreatePostUiState.Error
        }
    }

    fun onSave(post: Post) {
        // Does nothing yet
    }
}

sealed interface CreatePostUiState {
    data object Loading : CreatePostUiState
    data class Success(val suburbs: List<String>, val categories: List<String>) : CreatePostUiState
    data object Error : CreatePostUiState
}