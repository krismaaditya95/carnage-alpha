package com.snister.carnagealpha.features.expense_tracker.data.data_sources.local

import androidx.room.Embedded
import androidx.room.Relation
import com.snister.carnagealpha.features.expense_tracker.data.models.SourceLedgerDataModel
import com.snister.carnagealpha.features.expense_tracker.data.models.SpendingDataModel

data class LedgerAndSpendings(
    @Embedded
    val sourceLedger: SourceLedgerDataModel,

    @Relation(
        parentColumn = "sourceLedgerId",
        entityColumn = "sourceLedgerId"
    )
    val spendings: List<SpendingDataModel>
)
