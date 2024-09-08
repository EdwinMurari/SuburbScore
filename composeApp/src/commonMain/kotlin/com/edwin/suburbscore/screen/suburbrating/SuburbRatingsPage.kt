package com.edwin.suburbscore.screen.suburbrating

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.edwin.suburbscore.model.SuburbRating

@Composable
fun SuburbRatingsPage(
    viewModel: SuburbRatingsViewModel = viewModel { SuburbRatingsViewModel() }
) {
    val uiState by viewModel.uiState.collectAsState()

    SuburbRatingUi(uiState)
}

@Composable
private fun SuburbRatingUi(
    uiState: SuburbRatingsUiState
) {
    AnimatedContent(uiState) { targetState ->
        when (targetState) {
            SuburbRatingsUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is SuburbRatingsUiState.Success -> {
                SuburbRatingsList(targetState.suburbRatings)
            }

            is SuburbRatingsUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Something went wrong", modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}

@Composable
fun SuburbRatingsList(suburbRatings: List<SuburbRating>) {
    LazyColumn {
        items(suburbRatings, key = { it.name }) { rating ->
            SuburbRatingItem(rating)
        }
    }
}

@Composable
fun SuburbRatingItem(rating: SuburbRating) {
    Card(
        elevation = 2.dp, modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = rating.name, fontSize = 18.sp)
            Spacer(modifier = Modifier.weight(1f)) // Push rating to the right
            Text(text = "Rating: ${rating.rating}", fontSize = 16.sp)
        }
    }
}