package com.snister.carnagealpha.features.expense_tracker.presentation.upsert_spending

sealed interface UpsertSpendingAction {

    data class OnSpendingAmountChanged(
        val newSpendingAmountInput: String
    ): UpsertSpendingAction

    data class OnSpendingNameChanged(
        val newSpendingNameInput: String
    ): UpsertSpendingAction

    data object OnSpendingSaved : UpsertSpendingAction
}