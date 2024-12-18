package com.snister.carnagealpha.features.expense_tracker.presentation.dashboard_overview

sealed interface DashboardOverviewAction {
    data object LoadSpendingOverviewAndBalance: DashboardOverviewAction

    data object LoadSourceLedgerList: DashboardOverviewAction

    data object LoadSourceLedgerById: DashboardOverviewAction

    data class OnDateChange(val newDate: Int): DashboardOverviewAction

    data class OnDeleteSpending(val spendingId: Int): DashboardOverviewAction
}
