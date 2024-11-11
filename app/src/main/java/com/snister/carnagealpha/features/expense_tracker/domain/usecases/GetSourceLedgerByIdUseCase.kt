package com.snister.carnagealpha.features.expense_tracker.domain.usecases

import com.snister.carnagealpha.features.expense_tracker.domain.entities.SourceLedgerEntity
import com.snister.carnagealpha.features.expense_tracker.domain.repository.SourceLedgerRepository

class GetSourceLedgerByIdUseCase(
    private val sourceLedgerRepository: SourceLedgerRepository
){
    suspend operator fun invoke(id: Int): SourceLedgerEntity{
        return sourceLedgerRepository.getSourceLedgerById(id)
    }
}