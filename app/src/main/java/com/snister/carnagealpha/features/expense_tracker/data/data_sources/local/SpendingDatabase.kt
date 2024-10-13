package com.snister.carnagealpha.features.expense_tracker.data.data_sources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.snister.carnagealpha.features.expense_tracker.data.models.SpendingDataModel

@Database(
    entities = [SpendingDataModel::class],
    version = 1
)
abstract class SpendingDatabase: RoomDatabase() {
    abstract val dao: SpendingDao
}