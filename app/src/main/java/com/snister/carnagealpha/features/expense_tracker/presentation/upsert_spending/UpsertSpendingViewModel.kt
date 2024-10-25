package com.snister.carnagealpha.features.expense_tracker.presentation.upsert_spending

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snister.carnagealpha.features.expense_tracker.domain.repository.LocalRepository
import kotlinx.coroutines.launch

class UpsertSpendingViewModel(
    private val localRepository: LocalRepository
) : ViewModel() {

    var state by mutableStateOf(UpsertSpendingState())
        private set

    init {
        viewModelScope.launch {
            state = state.copy(
                initialBalance = localRepository.getBalance(),
                tempBalance = localRepository.getBalance()
            )
        }
    }

    fun onAction(action: UpsertSpendingAction){
        when(action){
            is UpsertSpendingAction.OnSpendingAmountChanged -> {
                state = state.copy(
                    tempBalance = (state.initialBalance - (action.newSpendingAmountInput.toLongOrNull() ?: 0)),
                    spendingAmountInput = action.newSpendingAmountInput,
                    spendingNameInput = action.newSpendingNameInput
                )
            }

            UpsertSpendingAction.OnSpendingSaved -> {
                viewModelScope.launch {
                    localRepository.updateBalance(state.tempBalance)
                    // insert spending to database
                    // ....
                }
            }
        }
    }
}