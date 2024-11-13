package com.snister.carnagealpha.features.expense_tracker.presentation.income_overview

sealed interface IncomeOverviewAction {
    data object LoadIncomeOverviewAndBalance: IncomeOverviewAction

    data object ShowIncomeDatePicker: IncomeOverviewAction
    data object HideIncomeDatePicker: IncomeOverviewAction
//    data class OnIncomeDateChange(val selectedDate: Long): IncomeOverviewAction
    data class OnIncomeDateRangePickerChange(val selectedDateRange: Pair<Long?, Long?>): IncomeOverviewAction

    data class OnDeleteIncome(val spendingId: Int): IncomeOverviewAction
}
