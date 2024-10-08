package com.edwin.suburbscore.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Article
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LocalCafe
import androidx.compose.material.icons.rounded.Traffic
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edwin.suburbscore.model.Post

@Composable
fun PostItem(
    post: Post,
    onPostClick: (Post) -> Unit
) {
    Card(elevation = 2.dp) { // Add some elevation to the Card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onPostClick(post) }
                .padding(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                CategoryTag(category = post.category)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = post.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = post.description,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp)) // Increased spacing

            // Location Row
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.LocationOn,
                    contentDescription = "Location",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = post.suburb.uppercase(),
                    style = MaterialTheme.typography.overline,
                    modifier = Modifier.wrapContentSize(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(4.dp)) // Reduced spacing

            // Engagement Row
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.ThumbUp,
                    contentDescription = "Upvotes",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${post.upVotes}",
                    fontSize = 12.sp,
                    modifier = Modifier.wrapContentSize(Alignment.Center)
                )

                Spacer(modifier = Modifier.width(16.dp)) // Increased spacing

                Icon(
                    Icons.Rounded.Visibility,
                    contentDescription = "Views",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${post.views}",
                    fontSize = 12.sp,
                    modifier = Modifier.wrapContentSize(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(4.dp)) // Reduced spacing

            // Date & Time Row
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Rounded.CalendarToday,
                    contentDescription = "Date",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = post.date,
                    fontSize = 12.sp,
                    modifier = Modifier.wrapContentSize(Alignment.Center)
                )

                Spacer(modifier = Modifier.width(16.dp)) // Increased spacing

                Icon(
                    Icons.Default.AccessTime,
                    contentDescription = "Time",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = post.time,
                    fontSize = 12.sp,
                    modifier = Modifier.wrapContentSize(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun CategoryIcon(category: String) {
    val icon: ImageVector = when (category.lowercase()) {
        "affordable housing" -> Icons.Rounded.Home
        "amenities" -> Icons.Rounded.LocalCafe
        "traffic" -> Icons.Rounded.Traffic
        "crime" -> Icons.Rounded.Warning
        else -> Icons.AutoMirrored.Rounded.Article // Default icon
    }
    Icon(icon, contentDescription = category, modifier = Modifier.size(20.dp))
}

@Composable
fun CategoryTag(category: String) {
    val categoryColor = when (category.lowercase()) {
        "affordable housing" -> LightGreen
        "amenities" -> LightBlue
        "traffic" -> Orange
        "crime" -> Red
        else -> LightGray
    }

    Row(
        modifier = Modifier
            .background(
                color = categoryColor.copy(alpha = 0.5f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CategoryIcon(category)

        Spacer(Modifier.width(4.dp))

        Text(
            text = category.uppercase(),
            style = MaterialTheme.typography.overline
        )
    }
}

val LightGreen = Color(0xFF90EE90) // Example Light Green color
val LightBlue = Color(0xFFADD8E6) // Example Light Blue color
val Orange = Color(0xFFFFA500)     // Example Orange color
val Red = Color(0xFFFF0000)         // Example Red color
val LightGray = Color(0xFFD3D3D3) // Example Light Gray color