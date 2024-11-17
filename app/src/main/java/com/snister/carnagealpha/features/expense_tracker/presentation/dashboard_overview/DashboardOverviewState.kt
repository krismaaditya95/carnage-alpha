package com.snister.carnagealpha.features.expense_tracker.presentation.dashboard_overview

import com.snister.carnagealpha.features.expense_tracker.domain.entities.IncomeAndSpendingEntity
import com.snister.carnagealpha.features.expense_tracker.domain.entities.IncomeEntity
import com.snister.carnagealpha.features.expense_tracker.domain.entities.SourceLedgerEntity
import com.snister.carnagealpha.features.expense_tracker.domain.entities.SpendingEntity
import java.time.ZonedDateTime

data class DashboardOverviewState(
    val spendingList: List<SpendingEntity> = emptyList(),
    val incomeList: List<IncomeEntity> = emptyList(),
    val incomeAndSpendingList: List<IncomeAndSpendingEntity> = emptyList(),
    val datesList: List<ZonedDateTime> = emptyList(),
    val balance: Long = 0,
    val pickedDate: ZonedDateTime = ZonedDateTime.now(),
    val isDatePickerDropDownMenuVisible: Boolean = false,
    val sourceLedgerList: List<SourceLedgerEntity> = emptyList(),

    val currentActiveSourceLedgerId: Int = 0,
    val currentSourceLedger: SourceLedgerEntity =
        SourceLedgerEntity(
            sourceLedgerId = 0,
            sourceLedgerName = "",
            sourceLedgerBalance = 0
        )
)
