package com.snister.carnagealpha.features.expense_tracker.domain.usecases

import com.snister.carnagealpha.features.expense_tracker.domain.entities.IncomeEntity
import com.snister.carnagealpha.features.expense_tracker.domain.repository.IncomeDataRepository

class UpsertIncomeUseCase(
    private val incomeDataRepository: IncomeDataRepository
) {

    suspend operator fun invoke(incomeEntity: IncomeEntity): Boolean{
        if(incomeEntity.incomeSourceName.isBlank()){
            return false
        }
        if(incomeEntity.incomeAmount <= 0){
            return false
        }

        incomeDataRepository.upsertIncome(incomeEntity)
        return true
    }
}