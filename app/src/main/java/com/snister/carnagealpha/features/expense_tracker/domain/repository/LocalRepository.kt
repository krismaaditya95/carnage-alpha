package com.snister.carnagealpha.features.expense_tracker.domain.repository

interface LocalRepository {

    suspend fun getBalance(): Long
    suspend fun updateBalance(balance: Long)
}