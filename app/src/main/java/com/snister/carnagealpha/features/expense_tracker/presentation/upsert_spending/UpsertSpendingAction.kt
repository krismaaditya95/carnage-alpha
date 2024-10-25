package com.snister.carnagealpha.features.expense_tracker.presentation.upsert_spending

sealed interface UpsertSpendingAction {

    data class OnSpendingAmountChanged(
        val newSpendingAmountInput: String,
        val newSpendingNameInput: String
    ): UpsertSpendingAction

    data object OnSpendingSaved : UpsertSpendingAction
}