package com.snister.carnagealpha.features.expense_tracker.presentation.income_overview

import com.snister.carnagealpha.features.expense_tracker.domain.entities.IncomeEntity
import com.snister.carnagealpha.features.expense_tracker.domain.entities.SpendingEntity
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

data class IncomeOverviewState(
    val incomeList: List<IncomeEntity> = emptyList(),
    val datesList: List<ZonedDateTime> = emptyList(),
    val balance: Long = 0,
    val selectedDateFromDatePicker: String = DateTimeFormatter.ofPattern("dd-MMMM-yyyy").format(ZonedDateTime.now()),
    val selectedDateRangeFromDateRangePicker: String = DateTimeFormatter.ofPattern("dd-MMMM-yyyy").format(ZonedDateTime.now()),
    val pickedDate: ZonedDateTime = ZonedDateTime.now(),
    val pickedDateRange: Pair<ZonedDateTime, ZonedDateTime> = Pair(
        ZonedDateTime.of(
            ZonedDateTime.now().year,
            ZonedDateTime.now().month.value,
            1, 0, 0, 0, 0, ZoneId.systemDefault()), ZonedDateTime.now()
    ),
    val totalIncomesBySelectedDateRange: Long = 0,
    val isDatePickerVisible: Boolean = false,
)
