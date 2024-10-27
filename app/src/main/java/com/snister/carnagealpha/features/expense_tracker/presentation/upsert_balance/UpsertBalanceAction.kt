package com.snister.carnagealpha.features.expense_tracker.presentation.upsert_balance

sealed interface UpsertBalanceAction {
    data class OnBalanceChanged(
        val newIncomeAmountInput: String
    ): UpsertBalanceAction

    data class OnIncomeSourceNameChanged(
        val newIncomeSourceNameInput: String
    ): UpsertBalanceAction

    data object OnBalanceSaved : UpsertBalanceAction

}