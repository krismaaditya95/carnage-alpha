package com.snister.carnagealpha.features.expense_tracker.presentation.upsert_spending

data class UpsertSpendingState(
    val initialBalance: Long = 0,
    val tempBalance: Long = 0,
    val spendingAmountInput: String = "",
    val spendingNameInput: String = ""
)
