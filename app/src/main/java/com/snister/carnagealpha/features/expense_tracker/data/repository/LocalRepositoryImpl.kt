package com.snister.carnagealpha.features.expense_tracker.data.repository

import android.content.SharedPreferences
import com.snister.carnagealpha.features.expense_tracker.domain.repository.LocalRepository

class LocalRepositoryImpl(
    private val prefs: SharedPreferences
): LocalRepository{

    companion object {
        private const val KEY_BALANCE = "KEY_BALANCE"
    }

    override suspend fun getBalance(): Long {
        return prefs.getLong(KEY_BALANCE, 0)
    }

    override suspend fun updateBalance(balance: Long) {
        prefs.edit().putLong(KEY_BALANCE, balance).apply()
    }
}