package com.edwin.suburbscore.screen.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DashboardScreen(modifier: Modifier = Modifier) {
    Column {
        DashboardItem("Rental Rates", ::openRentalRatesDashboard)
        DashboardItem("Locality Profile", ::openLocalityProfileDashboard)
    }
}

@Composable
fun DashboardItem(title: String, onClick: () -> Unit) {
    Card(
        elevation = 2.dp, modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, fontSize = 18.sp)
        }
    }
}