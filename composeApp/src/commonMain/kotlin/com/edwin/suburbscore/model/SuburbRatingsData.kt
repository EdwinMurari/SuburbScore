package com.edwin.suburbscore.model

import kotlinx.serialization.Serializable

@Serializable
data class SuburbRatingsData(
    val suburbs: List<SuburbRating>
)

@Serializable
data class SuburbRating(
    val name: String,
    val rating: Double
)