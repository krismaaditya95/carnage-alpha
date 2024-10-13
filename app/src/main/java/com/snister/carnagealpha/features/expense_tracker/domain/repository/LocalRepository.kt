package com.snister.carnagealpha.features.expense_tracker.domain.repository

interface LocalRepository {

    suspend fun getBalance(): Double
    suspend fun updateBalance(balance: Double)
}