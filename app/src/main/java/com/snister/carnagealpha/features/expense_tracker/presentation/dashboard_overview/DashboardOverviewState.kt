package com.snister.carnagealpha.features.expense_tracker.presentation.dashboard_overview

import com.snister.carnagealpha.features.expense_tracker.domain.entities.SourceLedgerEntity
import com.snister.carnagealpha.features.expense_tracker.domain.entities.SpendingEntity
import java.time.ZonedDateTime

data class DashboardOverviewState(
    val spendingList: List<SpendingEntity> = emptyList(),
    val datesList: List<ZonedDateTime> = emptyList(),
    val balance: Long = 0,
    val pickedDate: ZonedDateTime = ZonedDateTime.now(),
    val isDatePickerDropDownMenuVisible: Boolean = false,
    val sourceLedgerList: List<SourceLedgerEntity> = emptyList()
)
