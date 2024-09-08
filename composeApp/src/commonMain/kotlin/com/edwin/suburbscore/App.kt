package com.edwin.suburbscore

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.edwin.suburbscore.screen.postlist.PostListScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        PostListScreen()
    }
}