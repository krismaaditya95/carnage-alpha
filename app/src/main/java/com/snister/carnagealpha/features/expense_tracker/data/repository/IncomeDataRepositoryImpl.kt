package com.snister.carnagealpha.features.expense_tracker.data.repository

import com.snister.carnagealpha.features.expense_tracker.data.data_sources.local.IncomeDao
import com.snister.carnagealpha.features.expense_tracker.data.mapper.toIncomeDataModel
import com.snister.carnagealpha.features.expense_tracker.data.mapper.toIncomeEntity
import com.snister.carnagealpha.features.expense_tracker.domain.entities.IncomeEntity
import com.snister.carnagealpha.features.expense_tracker.domain.repository.IncomeDataRepository
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

class IncomeDataRepositoryImpl(
    private val dao: IncomeDao
): IncomeDataRepository {

    override suspend fun getAllIncomes(): List<IncomeEntity> {
        return dao.getAllIncomes()
            .map { it.toIncomeEntity() }
    }

    override suspend fun getIncome(id: Int): IncomeEntity {
        return dao.getIncome(id).toIncomeEntity()
    }

    override suspend fun upsertIncome(incomeEntity: IncomeEntity) {
        dao.upsertIncome(incomeEntity.toIncomeDataModel())
    }

    override suspend fun getTotalIncome(): Long {
        return dao.getTotalIncome() ?: 0
    }

    override suspend fun deleteIncome(id: Int) {
        dao.deleteIncome(id)
    }

    override suspend fun getIncomesByDate(dateTimeUtc: ZonedDateTime): List<IncomeEntity> {
        return dao.getAllIncomes()
            .map {it.toIncomeEntity()}
            .filter { incomeEntity ->
                incomeEntity.dateTime.dayOfMonth == dateTimeUtc.dayOfMonth &&
                        incomeEntity.dateTime.month == dateTimeUtc.month &&
                        incomeEntity.dateTime.year == dateTimeUtc.year

            }
    }

    override suspend fun getAllDates(): List<ZonedDateTime> {
        val uniqueDates = mutableSetOf<ZonedDateTime>()
        return dao.getAllDates()
            .map { Instant.parse(it).atZone(ZoneId.systemDefault()) }
            .filter { uniqueDates.add(it) }
    }
}