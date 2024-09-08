package com.edwin.suburbscore.screen.postlist.createpost

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.edwin.suburbscore.component.CategoryDropdown
import com.edwin.suburbscore.component.SuburbDropdown
import com.edwin.suburbscore.model.Post
import com.edwin.suburbscore.screen.createpost.CreatePostUiState
import com.edwin.suburbscore.screen.createpost.CreatePostViewModel

@Composable
fun CreatePostForm(
    modifier: Modifier = Modifier,
    viewModel: CreatePostViewModel = viewModel { CreatePostViewModel() },
    onClose: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Surface(shape = RoundedCornerShape(corner = CornerSize(16.dp))) {
        CreatePostUi(
            modifier = modifier,
            uiState = uiState,
            onSave = {
                viewModel.onSave(it)
                onClose()
            },
            onClose = onClose
        )
    }
}

@Composable
fun CreatePostUi(
    modifier: Modifier = Modifier,
    uiState: CreatePostUiState,
    onClose: () -> Unit,
    onSave: (Post) -> Unit
) {
    AnimatedContent(uiState) { targetState ->
        when (targetState) {
            CreatePostUiState.Error -> {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Something went wrong", modifier = Modifier.padding(16.dp))
                }
            }

            CreatePostUiState.Loading -> {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is CreatePostUiState.Success -> {
                CreatePostContent(
                    modifier = modifier,
                    uiState = targetState,
                    onClose = onClose,
                    onSave = onSave
                )
            }
        }
    }
}

@Composable
fun CreatePostContent(
    modifier: Modifier = Modifier,
    uiState: CreatePostUiState.Success,
    onClose: () -> Unit,
    onSave: (Post) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(uiState.categories.firstOrNull() ?: "") }
    var selectedSuburb by remember { mutableStateOf(uiState.suburbs.firstOrNull() ?: "") }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Create New Post", style = MaterialTheme.typography.h6)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Category Dropdown
        CategoryDropdown(uiState.categories, selectedCategory) { newCategory ->
            selectedCategory = newCategory
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Suburb Dropdown
        SuburbDropdown(uiState.suburbs, selectedSuburb) { newSuburb ->
            selectedSuburb = newSuburb
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onClose) {
                Text("Cancel")
            }
            Button(onClick = {
                val newPost = Post(
                    id = 0, // You'll need to generate a unique ID
                    title = title,
                    description = description,
                    category = selectedCategory,
                    suburb = selectedSuburb,
                    upVotes = 0,
                    views = 0,
                    date = "2024-01-01", // Replace with current date
                    time = "10:00" // Replace with current time
                )
                onSave(newPost)
            }) {
                Text("Save")
            }
        }
    }
}