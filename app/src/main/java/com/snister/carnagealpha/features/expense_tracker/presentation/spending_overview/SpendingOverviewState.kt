package com.snister.carnagealpha.features.expense_tracker.presentation.spending_overview

import com.snister.carnagealpha.features.expense_tracker.domain.entities.SourceLedgerEntity
import com.snister.carnagealpha.features.expense_tracker.domain.entities.SpendingEntity
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

data class SpendingOverviewState(
    val spendingList: List<SpendingEntity> = emptyList(),
    val datesList: List<ZonedDateTime> = emptyList(),
    val balance: Long = 0,
    val selectedDateFromDatePicker: String = DateTimeFormatter.ofPattern("dd-MMMM-yyyy").format(ZonedDateTime.now()),
    val totalSpending: Long = 0,
    val totalSpendingByDate: Long = 0,
    val pickedDate: ZonedDateTime = ZonedDateTime.now(),
    val isDatePickerVisible: Boolean = false,

    val currentActiveSourceLedgerId: Int = 0,
    val currentSourceLedger: SourceLedgerEntity =
        SourceLedgerEntity(
            sourceLedgerId = 0,
            sourceLedgerName = "",
            sourceLedgerBalance = 0
        ),

    val disableButton: Boolean = false
)
