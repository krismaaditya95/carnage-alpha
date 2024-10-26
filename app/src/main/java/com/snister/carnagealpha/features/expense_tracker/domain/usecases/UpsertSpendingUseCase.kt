package com.snister.carnagealpha.features.expense_tracker.domain.usecases

import com.snister.carnagealpha.features.expense_tracker.domain.entities.SpendingEntity
import com.snister.carnagealpha.features.expense_tracker.domain.repository.SpendingDataRepository

class UpsertSpendingUseCase(
    private val spendingDataRepository: SpendingDataRepository
) {

    suspend operator fun invoke(spendingEntity: SpendingEntity): Boolean{
        if(spendingEntity.spendingName.isBlank()){
            return false
        }
        if(spendingEntity.spendingAmount <= 0){
            return false
        }

        spendingDataRepository.upsertSpending(spendingEntity)
        return true
    }
}