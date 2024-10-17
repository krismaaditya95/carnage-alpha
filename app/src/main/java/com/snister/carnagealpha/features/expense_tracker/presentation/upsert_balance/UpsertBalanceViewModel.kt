package com.snister.carnagealpha.features.expense_tracker.presentation.upsert_balance

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snister.carnagealpha.features.expense_tracker.domain.repository.LocalRepository
import kotlinx.coroutines.launch

class UpsertBalanceViewModel(
    private val localRepository: LocalRepository
) : ViewModel(){

    var state by mutableStateOf(UpsertBalanceState())
        private set

    init {
        viewModelScope.launch {
            state = state.copy(
                balance = localRepository.getBalance()
            )
        }
    }

    fun onAction(action: UpsertBalanceAction){
        when (action){
            is UpsertBalanceAction.OnBalanceChanged -> {
                state = state.copy(
                    balance = action.newBalance
                )
            }

            UpsertBalanceAction.OnBalanceSaved -> {
                viewModelScope.launch {
                    localRepository.updateBalance(state.balance)
                }
            }
        }
    }
}