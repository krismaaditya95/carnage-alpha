package com.snister.carnagealpha.features.expense_tracker.presentation.income_overview

import com.snister.carnagealpha.features.expense_tracker.domain.entities.IncomeEntity
import com.snister.carnagealpha.features.expense_tracker.domain.entities.SpendingEntity
import java.time.ZonedDateTime

data class IncomeOverviewState(
    val incomeList: List<IncomeEntity> = emptyList(),
    val datesList: List<ZonedDateTime> = emptyList(),
    val balance: Long = 0,
    val pickedDate: ZonedDateTime = ZonedDateTime.now(),
    val isDatePickerDropDownMenuVisible: Boolean = false,
)
