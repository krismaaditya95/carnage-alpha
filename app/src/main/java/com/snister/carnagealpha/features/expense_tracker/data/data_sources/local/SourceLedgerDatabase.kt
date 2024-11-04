package com.snister.carnagealpha.features.expense_tracker.data.data_sources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.snister.carnagealpha.features.expense_tracker.data.models.SourceLedgerDataModel
import com.snister.carnagealpha.features.expense_tracker.data.models.SpendingDataModel

@Database(
    entities = [SourceLedgerDataModel::class, SpendingDataModel::class],
    version = 1
)
abstract class SourceLedgerDatabase: RoomDatabase() {
    abstract val sourceLedgerDao: SourceLedgerDao
}