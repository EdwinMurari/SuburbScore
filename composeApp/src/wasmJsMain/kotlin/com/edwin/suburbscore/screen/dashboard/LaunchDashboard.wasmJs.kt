package com.edwin.suburbscore.screen.dashboard

import kotlinx.browser.document

actual fun openLocalityProfileDashboard() {
    document.open(
        "https://public.tableau.com/views/Dashboard_17257674007270/LocalityProfileDashboard?:language=en-US&:sid=&:redirect=auth&:display_count=n&:origin=viz_share_link",
        "",
        "noopener=true"
    )
}

actual fun openRentalRatesDashboard() {
    document.open(
        "https://public.tableau.com/views/Rental_Rates/Dashboard2?:language=en-US&:sid=&:redirect=auth&:display_count=n&:origin=viz_share_link",
        "",
        "noopener=true"
    )
}