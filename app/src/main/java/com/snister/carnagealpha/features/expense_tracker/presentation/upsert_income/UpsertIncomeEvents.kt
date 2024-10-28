package com.snister.carnagealpha.features.expense_tracker.presentation.upsert_income

sealed interface UpsertIncomeEvents {

    data object UpsertIncomeSuccess: UpsertIncomeEvents
    data object UpsertIncomeFailed: UpsertIncomeEvents
}