package com.snister.carnagealpha.features.expense_tracker.presentation.upsert_balance

sealed interface UpsertBalanceEvents {

    data object UpsertBalanceSuccess: UpsertBalanceEvents
    data object UpsertBalanceFailed: UpsertBalanceEvents
}