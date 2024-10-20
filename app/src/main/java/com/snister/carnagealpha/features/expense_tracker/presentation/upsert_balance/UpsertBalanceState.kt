package com.snister.carnagealpha.features.expense_tracker.presentation.upsert_balance

data class UpsertBalanceState(
    val initialBalance: Long = 0,
    val tempBalance: Long = 0,
    val income: String = ""
)
