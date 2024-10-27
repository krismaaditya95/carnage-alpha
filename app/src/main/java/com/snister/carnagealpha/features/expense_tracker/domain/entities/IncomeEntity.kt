package com.snister.carnagealpha.features.expense_tracker.domain.entities

import java.time.ZonedDateTime

data class IncomeEntity(
    val incomeId: Int?,
    val incomeSourceName: String,
    val incomeAmount: Long,
    val dateTime: ZonedDateTime,
)
