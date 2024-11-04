package com.snister.carnagealpha.features.expense_tracker.data.data_sources.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.snister.carnagealpha.features.expense_tracker.data.models.SourceLedgerDataModel

@Dao
interface SourceLedgerDao {

    @Upsert
    suspend fun upsertSourceLedger(sourceLedger: SourceLedgerDataModel)

    @Query("SELECT * FROM sourceledgerdatamodel")
    suspend fun getAllSourceLedger(): List<SourceLedgerDataModel>

    @Transaction
    @Query("SELECT * FROM sourceledgerdatamodel")
    suspend fun getSourceLedgerWithSpendings(): List<LedgerAndSpendings>
}