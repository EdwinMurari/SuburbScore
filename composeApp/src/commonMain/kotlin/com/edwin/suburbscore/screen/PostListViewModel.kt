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

    init {
        viewModelScope.launch {
            val byteArray = Res.readBytes("files/posts.json").decodeToString()
            val postLists = Json.decodeFromString<List<Post>>(byteArray)
            _uiState.value = PostListUiState.Success(postLists)
        }
    }
}