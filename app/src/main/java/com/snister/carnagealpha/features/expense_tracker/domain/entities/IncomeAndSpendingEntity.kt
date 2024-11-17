package com.snister.carnagealpha.features.expense_tracker.domain.entities

import java.time.ZonedDateTime

data class IncomeAndSpendingEntity(
    val id: Int?,
    val name: String,
    val amount: Long,
    val dateTime: ZonedDateTime,
    val sourceLedgerId: Int,
    val type: String
)