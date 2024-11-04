package com.snister.carnagealpha.features.expense_tracker.domain.entities

data class SourceLedgerEntity(
    val sourceLedgerId: Int?,
    val sourceLedgerName: String,
    val sourceLedgerBalance: Long
)
