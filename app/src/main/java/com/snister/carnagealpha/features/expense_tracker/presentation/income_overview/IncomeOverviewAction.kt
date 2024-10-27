package com.snister.carnagealpha.features.expense_tracker.presentation.income_overview

sealed interface IncomeOverviewAction {
    data object LoadIncomeOverviewAndBalance: IncomeOverviewAction

    data object ShowDatePicker: IncomeOverviewAction
    data object HideDatePicker: IncomeOverviewAction
    data class OnDateChange(val selectedDate: Long): IncomeOverviewAction

    data class OnDeleteIncome(val spendingId: Int): IncomeOverviewAction
}
