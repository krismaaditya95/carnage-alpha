package com.snister.carnagealpha.features.expense_tracker.domain.usecases

import com.snister.carnagealpha.features.expense_tracker.domain.entities.SourceLedgerEntity
import com.snister.carnagealpha.features.expense_tracker.domain.repository.SourceLedgerRepository

class GetAllSourceLedgerUseCase(
    private val sourceLedgerRepository: SourceLedgerRepository
){
    suspend operator fun invoke(): List<SourceLedgerEntity>{
        return sourceLedgerRepository.getAllSourceLedger()
    }
}