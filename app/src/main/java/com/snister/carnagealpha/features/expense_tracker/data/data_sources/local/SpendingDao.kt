package com.snister.carnagealpha.features.expense_tracker.data.data_sources.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.snister.carnagealpha.features.expense_tracker.data.models.SpendingDataModel

@Dao
interface SpendingDao {

    @Upsert
    suspend fun upsertSpending(entity: SpendingDataModel)

    @Query("SELECT * FROM spendingdatamodel")
    suspend fun getAllSpendings() : List<SpendingDataModel>

    @Query("SELECT * FROM spendingdatamodel WHERE spendingId = :id")
    suspend fun getSpending(id: Int): SpendingDataModel

    @Query("SELECT SUM(spendingAmount) FROM spendingdatamodel")
    suspend fun getTotalSpend(): Long?

    @Query("DELETE FROM spendingdatamodel WHERE spendingId = :id")
    suspend fun deleteSpending(id: Int)

    @Query("SELECT dateTime FROM spendingdatamodel")
    suspend fun getAllDates(): List<String>
}