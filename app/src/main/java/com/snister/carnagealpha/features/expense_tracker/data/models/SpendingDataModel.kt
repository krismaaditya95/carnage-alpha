package com.snister.carnagealpha.features.expense_tracker.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = SourceLedgerDataModel::class,
            parentColumns = arrayOf("sourceLedgerId"),
            childColumns = arrayOf("sourceLedgerId"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class SpendingDataModel(

    @PrimaryKey(autoGenerate = true)
    val spendingId: Int? = null,

    val spendingName: String,

    val spendingAmount: Long,

    val dateTime: String,

    @ColumnInfo(index = true)
    val sourceLedgerId: Int,
)