package com.edwin.suburbscore.screen.suburbrating

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edwin.suburbscore.model.SuburbRating
import com.edwin.suburbscore.model.SuburbRatingsData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi
import suburbscore.composeapp.generated.resources.Res

class SuburbRatingsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<SuburbRatingsUiState>(SuburbRatingsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private lateinit var suburbRatings: List<SuburbRating>

    init {
        viewModelScope.launch {
            loadSuburbRatingsFromJson("files/suburb_ratings.json")
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    private suspend fun loadSuburbRatingsFromJson(filePath: String) {
        try {
            val jsonString = Res.readBytes(filePath).decodeToString()
            suburbRatings = Json.decodeFromString<SuburbRatingsData>(jsonString).suburbs
            _uiState.value = SuburbRatingsUiState.Success(suburbRatings)
        } catch (e: Exception) {
            _uiState.value = SuburbRatingsUiState.Error("Error loading ratings: ${e.message}")
        }
    }
}

