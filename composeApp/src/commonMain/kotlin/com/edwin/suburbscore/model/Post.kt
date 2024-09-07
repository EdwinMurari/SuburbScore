package com.edwin.suburbscore.model

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Int,
    val title: String,
    val description: String,
    val suburb: String,
    val category: String,
    val upVotes: Int,
    val views: Int,
    val date: String,
    val time: String
)