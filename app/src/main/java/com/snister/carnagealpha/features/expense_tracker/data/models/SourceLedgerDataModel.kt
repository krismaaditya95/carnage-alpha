package com.snister.carnagealpha.features.expense_tracker.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SourceLedgerDataModel(

    @PrimaryKey(autoGenerate = true)
    val sourceLedgerId: Int? = null,

    val sourceLedgerName: String,

    val sourceLedgerBalance: Long
)
