package com.edwin.suburbscore.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SuburbDropdown(
    suburbs: List<String>,
    selectedSuburb: String?,
    onSuburbSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }

    // Display selected suburb or placeholder
    val displayText = selectedSuburb ?: "Select Suburb"

    Column {
        Box {
            // Clickable text to expand/collapse the dropdown
            Text(
                text = displayText,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(8.dp),
                fontSize = 16.sp
            )

            // Dropdown icon
            Icon(
                Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown",
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterEnd)
            )
        }

        // Dropdown content
        AnimatedVisibility(expanded) {
            val filteredSuburbs = if (searchText.isBlank()) {
                suburbs
            } else {
                suburbs.filter { it.contains(searchText, ignoreCase = true) }
            }

            // Suburb list
            LazyColumn(modifier = Modifier.heightIn(max = 200.dp)) {
                item {
                    // Search field
                    OutlinedTextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        label = { Text("Search suburbs") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }

                items(filteredSuburbs) { suburb ->
                    Text(
                        text = suburb,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onSuburbSelect(suburb)
                                expanded = false
                                searchText = ""
                            }
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}