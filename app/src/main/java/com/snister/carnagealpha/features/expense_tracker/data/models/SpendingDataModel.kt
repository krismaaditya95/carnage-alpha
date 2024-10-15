package com.snister.carnagealpha.features.expense_tracker.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SpendingDataModel(

    @PrimaryKey(autoGenerate = true)
    val spendingId: Int? = null,

    val spendingName: String,

    val spendingAmount: Double,

    val dateTime: String,
)