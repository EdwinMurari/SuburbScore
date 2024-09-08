package com.edwin.suburbscore.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
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
fun CategoryDropdown(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Surface(
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
            ),
            shape = RoundedCornerShape(4.dp)
        ) {
            Box {
                // Clickable text to expand/collapse the dropdown
                Text(
                    text = selectedCategory.titleCase(),
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
        }

        // Dropdown content
        AnimatedVisibility(expanded) {
            LazyColumn(modifier = Modifier.heightIn(max = 200.dp)) {
                items(categories, key = { it }) { category ->
                    Text(
                        text = category.titleCase(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onCategorySelect(category)
                                expanded = false
                            }
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}

fun String.titleCase() =
    split(" ").joinToString(" ") { it.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() } }
