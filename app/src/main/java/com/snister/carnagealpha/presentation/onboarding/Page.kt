package com.snister.carnagealpha.presentation.onboarding

import androidx.annotation.DrawableRes
import com.snister.carnagealpha.R

data class Page(
    val title: String,
    val description: String,
    @DrawableRes val image: Int
)

val pages = listOf(
    Page(
        title = "Dapatkan info terbaru tentang film",
        description = "Dari film yang sedang diputar di bioskop hingga film yang populer",
        image = R.drawable.onboarding_01
    ),
    Page(
        title = "Title 2",
        description = "Description 2",
        image = R.drawable.onboarding_02
    ),
    Page(
        title = "Title 3",
        description = "Description 3",
        image = R.drawable.onboarding_03
    ),
)
