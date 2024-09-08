package com.edwin.suburbscore.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FiltersSection(
    modifier: Modifier = Modifier,
    categories: List<String>,
    suburbs: List<String>,
    selectedCategories: Set<String>,
    selectedSuburb: String?,
    onCategorySelect: (String) -> Unit,
    onSuburbSelect: (String) -> Unit
) {
    Column(modifier = modifier.padding(16.dp)) {
        Text("Filters", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        // Category Filters
        Text("Categories", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        CategoryFilterList(categories, selectedCategories, onCategorySelect)

        Spacer(modifier = Modifier.height(16.dp))

        // Suburb Filter
        Text("Suburb", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        SuburbDropdown(suburbs, selectedSuburb, onSuburbSelect)
    }
}

@Composable
fun CategoryFilterList(
    categories: List<String>,
    selectedCategories: Set<String>,
    onCategorySelect: (String) -> Unit
) {
    LazyColumn {
        items(categories) { category ->
            Row(modifier = Modifier
                .fillMaxWidth()
                .clickable { onCategorySelect(category) }
                .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = category in selectedCategories,
                    onCheckedChange = { isChecked ->
                        if (isChecked) {
                            onCategorySelect(category)
                        } else {
                            onCategorySelect(category) // Assuming you want to toggle
                        }
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = category)
            }
        }
    }
}

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
                .align(Alignment.End)
        )

        // Dropdown content
        if (expanded) {
            // Search field
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                label = { Text("Search suburbs") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            val filteredSuburbs = if (searchText.isBlank()) {
                suburbs
            } else {
                suburbs.filter { it.contains(searchText, ignoreCase = true) }
            }

            // Suburb list
            LazyColumn(modifier = Modifier.heightIn(max = 200.dp)) {
                items(filteredSuburbs) { suburb ->
                    Text(
                        text = suburb,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onSuburbSelect(suburb)
                                expanded = false
                                searchText = "" // Clear search text after selection
                            }
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}