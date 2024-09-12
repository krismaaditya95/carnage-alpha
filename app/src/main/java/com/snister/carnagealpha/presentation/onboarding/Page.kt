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
        title = "Title 1",
        description = "Description 1",
        image = R.drawable.ic_launcher_background
    ),
    Page(
        title = "Title 2",
        description = "Description 2",
        image = R.drawable.ic_launcher_background
    ),
    Page(
        title = "Title 3",
        description = "Description 3",
        image = R.drawable.ic_launcher_background
    ),
)
