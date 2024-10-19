package com.snister.carnagealpha.features.expense_tracker.presentation.upsert_balance

sealed interface UpsertBalanceAction {
    data class OnBalanceChanged(
        val newBalance: Long,
        val newIncome: String
    ): UpsertBalanceAction

    data object OnBalanceSaved : UpsertBalanceAction

}