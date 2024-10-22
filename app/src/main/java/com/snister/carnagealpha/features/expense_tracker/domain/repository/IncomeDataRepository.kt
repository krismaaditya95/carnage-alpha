package com.snister.carnagealpha.features.expense_tracker.domain.repository

import com.snister.carnagealpha.features.expense_tracker.domain.entities.IncomeEntity
import java.time.ZonedDateTime

interface IncomeDataRepository {

    suspend fun getAllIncomes(): List<IncomeEntity>

    suspend fun getIncome(id: Int): IncomeEntity

    suspend fun upsertIncome(incomeEntity: IncomeEntity)

    suspend fun getTotalIncome(): Long

    suspend fun deleteIncome(id: Int)

    suspend fun getIncomesByDate(
        dateTimeUtc: ZonedDateTime
    ): List<IncomeEntity>

    suspend fun getAllDates(): List<ZonedDateTime>

}