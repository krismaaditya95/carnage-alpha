package com.snister.carnagealpha.features.expense_tracker.data.data_sources.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.snister.carnagealpha.features.expense_tracker.data.models.IncomeDataModel

@Dao
interface IncomeDao {

    @Upsert
    suspend fun upsertIncome(entity: IncomeDataModel)

    @Query("SELECT * FROM incomedatamodel")
    suspend fun getAllIncomes() : List<IncomeDataModel>

    @Query("SELECT * FROM incomedatamodel WHERE incomeId = :id")
    suspend fun getIncome(id: Int): IncomeDataModel

    @Query("SELECT SUM(incomeAmount) FROM incomedatamodel")
    suspend fun getTotalIncome(): Long?

    @Query("DELETE FROM incomedatamodel WHERE incomeId = :id")
    suspend fun deleteIncome(id: Int)

    @Query("SELECT dateTime FROM incomedatamodel")
    suspend fun getAllDates(): List<String>
}