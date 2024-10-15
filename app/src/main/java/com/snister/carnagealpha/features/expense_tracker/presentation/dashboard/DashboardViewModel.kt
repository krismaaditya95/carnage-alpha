package com.snister.carnagealpha.features.expense_tracker.presentation.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snister.carnagealpha.features.expense_tracker.domain.repository.LocalRepository
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val localRepository: LocalRepository
) : ViewModel(){

    var state by mutableStateOf(DashboardBalanceState())
        private set

    init {
        viewModelScope.launch {
            state = state.copy(
                balance = localRepository.getBalance()
            )
        }
    }

    fun onAction(action: DashboardBalanceAction){
        when (action){
            is DashboardBalanceAction.OnBalanceChanged -> {
                state = state.copy(
                    balance = action.newBalance
                )
            }

            DashboardBalanceAction.OnBalanceSaved -> {
                viewModelScope.launch {
                    localRepository.updateBalance(state.balance)
                }
            }
        }
    }
}