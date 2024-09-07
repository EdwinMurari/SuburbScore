package com.edwin.suburbscore.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Chip
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import com.edwin.suburbscore.model.Post
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostItem(post: Post) {
    Surface(
        modifier = Modifier
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                ) {
                    Row {
                        Text(
                            text = post.suburb.toUpperCase(Locale.current),
                            style = MaterialTheme.typography.overline
                        )

                        Chip(onClick = {}) {
                            Text(
                                text = post.category.toUpperCase(Locale.current),
                                style = MaterialTheme.typography.overline
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = post.title,
                        style = MaterialTheme.typography.h6
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = post.description,
                        style = MaterialTheme.typography.body1
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = post.date,
                        style = MaterialTheme.typography.caption
                    )
                }
            }

            Divider(modifier = Modifier.padding(start = 20.dp))
        }
    }
}

@Preview
@Composable
fun PreviewPostItem() {
    MaterialTheme {
        PostItem(
            post = Post(
                id = 10,
                title = "Anyone Else Love Living in St Kilda?",
                description = "St Kilda has its quirks, but the beachside lifestyle is hard to beat! Anyone else a fan?",
                category = "amenities",
                suburb = "St Kilda",
                upVotes = 55,
                views = 245,
                date = "2023-12-14",
                time = "13:35"
            )
        )
    }
}