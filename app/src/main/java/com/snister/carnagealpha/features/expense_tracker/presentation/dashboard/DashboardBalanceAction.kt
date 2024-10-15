package com.snister.carnagealpha.features.expense_tracker.presentation.dashboard

sealed interface DashboardBalanceAction {
    data class OnBalanceChanged(
        val newBalance: Double
    ): DashboardBalanceAction

    data object OnBalanceSaved : DashboardBalanceAction

}