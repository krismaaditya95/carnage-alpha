package com.snister.carnagealpha.features.expense_tracker.presentation.income_overview

sealed interface IncomeOverviewAction {
    data object LoadIncomeOverviewAndBalance: IncomeOverviewAction

    data class OnDateChange(val newDate: Int): IncomeOverviewAction

    data class OnDeleteIncome(val spendingId: Int): IncomeOverviewAction
}
