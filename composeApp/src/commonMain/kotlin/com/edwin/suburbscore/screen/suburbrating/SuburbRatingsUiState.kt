package com.edwin.suburbscore.screen.suburbrating

import com.edwin.suburbscore.model.SuburbRating

sealed interface SuburbRatingsUiState {
    data object Loading : SuburbRatingsUiState
    data class Success(val suburbRatings: List<SuburbRating>) : SuburbRatingsUiState
    data class Error(val message: String) : SuburbRatingsUiState
}