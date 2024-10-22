package com.snister.carnagealpha.features.expense_tracker.data.data_sources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.snister.carnagealpha.features.expense_tracker.data.models.IncomeDataModel

@Database(
    entities = [IncomeDataModel::class],
    version = 1
)
abstract class IncomeDatabase: RoomDatabase() {
    abstract val dao: IncomeDao
}