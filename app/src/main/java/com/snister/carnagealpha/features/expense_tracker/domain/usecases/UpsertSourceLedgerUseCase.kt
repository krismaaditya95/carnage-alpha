package com.snister.carnagealpha.features.expense_tracker.domain.usecases

import com.snister.carnagealpha.features.expense_tracker.domain.entities.SourceLedgerEntity
import com.snister.carnagealpha.features.expense_tracker.domain.repository.SourceLedgerRepository

class UpsertSourceLedgerUseCase(
    private val sourceLedgerRepository: SourceLedgerRepository
) {

    suspend operator fun invoke(sourceLedgerEntity: SourceLedgerEntity): Boolean{
        sourceLedgerRepository.upsertSourceLedger(sourceLedgerEntity)
        return true
    }
}