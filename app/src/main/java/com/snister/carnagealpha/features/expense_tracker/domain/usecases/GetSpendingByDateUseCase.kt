package com.snister.carnagealpha.features.expense_tracker.domain.usecases

import com.snister.carnagealpha.features.expense_tracker.domain.entities.SpendingEntity
import com.snister.carnagealpha.features.expense_tracker.domain.repository.SpendingDataRepository
import java.time.ZonedDateTime

class GetSpendingByDateUseCase(
    private val spendingDataRepository: SpendingDataRepository
) {

    suspend operator fun invoke(
        date: ZonedDateTime,
        sourceLedgerId: Int
    ): List<SpendingEntity>{

        return spendingDataRepository.getSpendingsByDate(
            date, sourceLedgerId
        )
    }
}