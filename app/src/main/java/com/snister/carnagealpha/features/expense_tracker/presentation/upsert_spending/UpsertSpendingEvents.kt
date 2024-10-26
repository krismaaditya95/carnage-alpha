package com.snister.carnagealpha.features.expense_tracker.presentation.upsert_spending

sealed interface UpsertSpendingEvents {

    data object UpsertSpendingSuccess: UpsertSpendingEvents
    data object UpsertSpendingFailed: UpsertSpendingEvents
}