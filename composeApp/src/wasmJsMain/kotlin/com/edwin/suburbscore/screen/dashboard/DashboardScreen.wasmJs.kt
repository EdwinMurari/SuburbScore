package com.edwin.suburbscore.screen.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.browser.document

@Composable
actual fun DashboardScreen(modifier: Modifier) {
    document.open(
        "https://public.tableau.com/views/Rental_Rates/Dashboard2?:language=en-US&:sid=&:redirect=auth&:display_count=n&:origin=viz_share_link",
        "",
        "noopener=true"
    )
}