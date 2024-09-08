package com.edwin.suburbscore.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.edwin.suburbscore.model.Post

@Composable
fun CreatePostForm(
    categories: List<String>,
    suburbs: List<String>,
    onClose: () -> Unit,
    onSave: (Post) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(categories.firstOrNull() ?: "") }
    var selectedSuburb by remember { mutableStateOf(suburbs.firstOrNull() ?: "") }

    Surface(shape = RoundedCornerShape(corner = CornerSize(16.dp))) {
        Column(
            modifier = Modifier
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
            CategoryDropdown(categories, selectedCategory) { newCategory ->
                selectedCategory = newCategory
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Suburb Dropdown
            SuburbDropdown(suburbs, selectedSuburb) { newSuburb ->
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
}