package com.snister.carnagealpha.features.expense_tracker.domain.repository

import com.snister.carnagealpha.features.expense_tracker.domain.entities.SourceLedgerEntity

interface SourceLedgerRepository {

    suspend fun upsertSourceLedger(sourceLedgerEntity: SourceLedgerEntity)

    suspend fun getAllSourceLedger(): List<SourceLedgerEntity>
}