package com.snister.carnagealpha.features.expense_tracker.presentation.spending_overview

import com.snister.carnagealpha.features.expense_tracker.domain.entities.SpendingEntity
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

data class SpendingOverviewState(
    val spendingList: List<SpendingEntity> = emptyList(),
    val datesList: List<ZonedDateTime> = emptyList(),
    val balance: Long = 0,
    val selectedDateFromDatePicker: String = DateTimeFormatter.ofPattern("dd-MMMM-yyyy").format(ZonedDateTime.now()),
    val pickedDate: ZonedDateTime = ZonedDateTime.now(),
    val isDatePickerVisible: Boolean = false,
)
