package com.snister.carnagealpha.features.expense_tracker.domain.repository

import com.snister.carnagealpha.features.expense_tracker.domain.entities.SpendingEntity
import java.time.ZonedDateTime

interface SpendingDataRepository {

    suspend fun getAllSpendings(): List<SpendingEntity>

    suspend fun getSpending(id: Int): SpendingEntity

    suspend fun upsertSpending(spendingEntity: SpendingEntity)

    suspend fun getTotalSpend(): Long

    suspend fun deleteSpending(id: Int)

    suspend fun getSpendingsByDate(
        dateTimeUtc: ZonedDateTime,
        sourceLedgerId: Int
    ): List<SpendingEntity>

    suspend fun getAllDates(): List<ZonedDateTime>

}