package com.snister.carnagealpha.features.expense_tracker.presentation.upsert_balance

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snister.carnagealpha.features.expense_tracker.domain.entities.IncomeEntity
import com.snister.carnagealpha.features.expense_tracker.domain.repository.LocalRepository
import com.snister.carnagealpha.features.expense_tracker.domain.usecases.UpsertIncomeUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class UpsertBalanceViewModel(
    private val localRepository: LocalRepository,
    private val upsertIncomeUseCase: UpsertIncomeUseCase
) : ViewModel(){

    var state by mutableStateOf(UpsertBalanceState())
        private set

    private val _upsertBalanceEventChannel = Channel<UpsertBalanceEvents>()
    val event = _upsertBalanceEventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            state = state.copy(
                initialBalance = localRepository.getBalance(),
                tempBalance = localRepository.getBalance()
            )
        }
    }

    fun onAction(action: UpsertBalanceAction){
        when (action){
            is UpsertBalanceAction.OnBalanceChanged -> {
                state = state.copy(
                    tempBalance = (state.initialBalance + (action.newIncomeAmountInput.toLongOrNull() ?: 0)),
                    incomeAmountInput = action.newIncomeAmountInput
                )
            }

            is UpsertBalanceAction.OnIncomeSourceNameChanged -> {
                state = state.copy(
                    incomeSourceNameInput = action.newIncomeSourceNameInput
                )
            }

            UpsertBalanceAction.OnBalanceSaved -> {
                viewModelScope.launch {
                    if(saveIncome()){
                        _upsertBalanceEventChannel.send(UpsertBalanceEvents.UpsertBalanceSuccess)
                        localRepository.updateBalance(state.tempBalance)
                    }else{
                        _upsertBalanceEventChannel.send(UpsertBalanceEvents.UpsertBalanceFailed)
                    }
                }
            }
        }
    }

    private suspend fun saveIncome(): Boolean{
        val income = IncomeEntity(
            incomeId = null,
            incomeAmount = state.incomeAmountInput.toLong(),
            incomeSourceName = state.incomeSourceNameInput,
            dateTime = ZonedDateTime.now()
        )

        return upsertIncomeUseCase(income)
    }
}