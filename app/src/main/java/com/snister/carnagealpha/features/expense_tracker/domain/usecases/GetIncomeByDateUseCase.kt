package com.snister.carnagealpha.features.expense_tracker.domain.usecases

import com.snister.carnagealpha.features.expense_tracker.domain.entities.IncomeEntity
import com.snister.carnagealpha.features.expense_tracker.domain.repository.IncomeDataRepository
import java.time.ZonedDateTime

class GetIncomeByDateUseCase(
    private val incomeDataRepository: IncomeDataRepository
) {

    suspend operator fun invoke(
        dateTimeRange: Pair<ZonedDateTime, ZonedDateTime>,
        sourceLedgerId: Int
    ): List<IncomeEntity>{
        return incomeDataRepository.getIncomesByDateRange(
            dateTimeRange,
            sourceLedgerId
        ).reversed()
    }
}