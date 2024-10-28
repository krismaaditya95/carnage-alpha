package com.snister.carnagealpha.features.expense_tracker.presentation.upsert_income

sealed interface UpsertIncomeAction {
    data class OnIncomeAmountChanged(
        val newIncomeAmountInput: String
    ): UpsertIncomeAction

    data class OnIncomeSourceNameChanged(
        val newIncomeSourceNameInput: String
    ): UpsertIncomeAction

    data object OnIncomeSaved : UpsertIncomeAction

}