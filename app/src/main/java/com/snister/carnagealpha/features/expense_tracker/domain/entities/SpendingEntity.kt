package com.snister.carnagealpha.features.expense_tracker.domain.entities

import java.time.ZonedDateTime

data class SpendingEntity(
    val spendingId: Int?,
    val spendingName: String,
    val spendingAmount: Long,
    val dateTime: ZonedDateTime,
)
