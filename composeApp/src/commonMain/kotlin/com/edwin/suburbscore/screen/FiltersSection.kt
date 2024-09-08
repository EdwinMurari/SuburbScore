package com.edwin.suburbscore.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edwin.suburbscore.component.SuburbDropdown

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