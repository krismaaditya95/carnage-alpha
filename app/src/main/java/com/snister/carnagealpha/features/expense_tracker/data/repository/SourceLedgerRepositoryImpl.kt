package com.snister.carnagealpha.features.expense_tracker.data.repository

import com.snister.carnagealpha.features.expense_tracker.data.data_sources.local.SourceLedgerDao
import com.snister.carnagealpha.features.expense_tracker.data.mapper.toSourceLedgerDataModel
import com.snister.carnagealpha.features.expense_tracker.data.mapper.toSourceLedgerEntity
import com.snister.carnagealpha.features.expense_tracker.domain.entities.SourceLedgerEntity
import com.snister.carnagealpha.features.expense_tracker.domain.repository.SourceLedgerRepository

class SourceLedgerRepositoryImpl(
    private val sourceLedgerDao: SourceLedgerDao
): SourceLedgerRepository {

    override suspend fun upsertSourceLedger(sourceLedgerEntity: SourceLedgerEntity) {
        sourceLedgerDao.upsertSourceLedger(sourceLedgerEntity.toSourceLedgerDataModel())
    }

    override suspend fun getAllSourceLedger(): List<SourceLedgerEntity> {
        return sourceLedgerDao.getAllSourceLedger()
            .map { it.toSourceLedgerEntity() }
    }
}