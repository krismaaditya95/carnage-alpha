package com.snister.carnagealpha.features.expense_tracker.presentation.income_overview

import com.snister.carnagealpha.features.expense_tracker.domain.entities.IncomeEntity
import com.snister.carnagealpha.features.expense_tracker.domain.entities.SpendingEntity
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

data class IncomeOverviewState(
    val incomeList: List<IncomeEntity> = emptyList(),
    val datesList: List<ZonedDateTime> = emptyList(),
    val balance: Long = 0,
    val selectedDateFromDatePicker: String = DateTimeFormatter.ofPattern("dd-MMMM-yyyy").format(ZonedDateTime.now()),
    val pickedDate: ZonedDateTime = ZonedDateTime.now(),
    val isDatePickerVisible: Boolean = false,
)
