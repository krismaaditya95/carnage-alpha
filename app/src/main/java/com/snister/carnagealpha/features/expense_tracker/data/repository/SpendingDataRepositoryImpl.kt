package com.snister.carnagealpha.features.expense_tracker.data.repository

import com.snister.carnagealpha.features.expense_tracker.data.data_sources.local.SpendingDao
import com.snister.carnagealpha.features.expense_tracker.data.mapper.toSpendingDataModel
import com.snister.carnagealpha.features.expense_tracker.data.mapper.toSpendingEntity
import com.snister.carnagealpha.features.expense_tracker.domain.entities.SpendingEntity
import com.snister.carnagealpha.features.expense_tracker.domain.repository.SpendingDataRepository
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

class SpendingDataRepositoryImpl(
    private val dao: SpendingDao
): SpendingDataRepository {

    override suspend fun getAllSpendings(): List<SpendingEntity> {
        return dao.getAllSpendings()
            .map { it.toSpendingEntity() }
    }

    override suspend fun getSpending(id: Int): SpendingEntity {
        return dao.getSpending(id).toSpendingEntity()
    }

    override suspend fun upsertSpending(spendingEntity: SpendingEntity) {
        dao.upsertSpending(spendingEntity.toSpendingDataModel())
    }

    override suspend fun getTotalSpend(): Double {
        return dao.getTotalSpend() ?: 0.0
    }

    override suspend fun deleteSpending(id: Int) {
        dao.deleteSpending(id)
    }

    override suspend fun getSpendingsByDate(dateTimeUtc: ZonedDateTime): List<SpendingEntity> {
        return dao.getAllSpendings()
            .map{ it.toSpendingEntity() }
            .filter { spendingEntity ->
                spendingEntity.dateTime.dayOfMonth == dateTimeUtc.dayOfMonth &&
                        spendingEntity.dateTime.month == dateTimeUtc.month &&
                        spendingEntity.dateTime.year == dateTimeUtc.year
            }
    }

    override suspend fun getAllDates(): List<ZonedDateTime> {
        val uniqueDates = mutableSetOf<ZonedDateTime>()
        return dao.getAllDates()
            .map { Instant.parse(it).atZone(ZoneId.of("UTC")) }
            .filter {
                uniqueDates.add(it)
            }
    }

}