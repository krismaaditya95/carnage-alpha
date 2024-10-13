package com.snister.carnagealpha.config

sealed interface ScreenRoutes {

    @kotlinx.serialization.Serializable
    data object SpendingOverview: ScreenRoutes

    @kotlinx.serialization.Serializable
    data class SpendingDetails(val spendingId: Int = -1) : ScreenRoutes

    @kotlinx.serialization.Serializable
    data object Balance: ScreenRoutes
}