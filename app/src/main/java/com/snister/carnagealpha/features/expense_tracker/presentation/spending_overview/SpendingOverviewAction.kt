package com.snister.carnagealpha.features.expense_tracker.presentation.spending_overview

sealed interface SpendingOverviewAction {
    data object LoadSpendingOverviewAndBalance: SpendingOverviewAction

    data object ShowDatePicker: SpendingOverviewAction

    data class OnDateChange(val newDate: Int): SpendingOverviewAction

    data class OnDeleteSpending(val spendingId: Int): SpendingOverviewAction
}
