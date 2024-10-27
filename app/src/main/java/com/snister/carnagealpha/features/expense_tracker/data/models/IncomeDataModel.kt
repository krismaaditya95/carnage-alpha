package com.snister.carnagealpha.features.expense_tracker.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class IncomeDataModel(

    @PrimaryKey(autoGenerate = true)
    val incomeId: Int? = null,

    val incomeSourceName: String,

    val incomeAmount: Long,

    val dateTime: String,
)