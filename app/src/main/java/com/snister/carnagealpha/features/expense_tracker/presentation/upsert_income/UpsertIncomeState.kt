package com.snister.carnagealpha.features.expense_tracker.presentation.upsert_income

data class UpsertIncomeState(
    val initialBalance: Long = 0,
    val tempBalance: Long = 0,
    val incomeAmountInput: String = "",
    val incomeSourceNameInput: String = "",
    val currentActiveSourceLedgerId: Int = 0,
    val currentActiveSourceLedgerName: String = ""
)
