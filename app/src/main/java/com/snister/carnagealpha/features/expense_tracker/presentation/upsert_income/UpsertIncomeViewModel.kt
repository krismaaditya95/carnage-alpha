package com.snister.carnagealpha.features.expense_tracker.presentation.upsert_income

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

class UpsertIncomeViewModel(
    private val localRepository: LocalRepository,
    private val upsertIncomeUseCase: UpsertIncomeUseCase
) : ViewModel(){

    var state by mutableStateOf(UpsertIncomeState())
        private set

    private val _upsertBalanceEventChannel = Channel<UpsertIncomeEvents>()
    val event = _upsertBalanceEventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            state = state.copy(
                initialBalance = localRepository.getBalance(),
                tempBalance = localRepository.getBalance()
            )
        }
    }

    fun onAction(action: UpsertIncomeAction){
        when (action){
            is UpsertIncomeAction.OnIncomeAmountChanged -> {
                state = state.copy(
                    tempBalance = (state.initialBalance + (action.newIncomeAmountInput.toLongOrNull() ?: 0)),
                    incomeAmountInput = action.newIncomeAmountInput
                )
            }

            is UpsertIncomeAction.OnIncomeSourceNameChanged -> {
                state = state.copy(
                    incomeSourceNameInput = action.newIncomeSourceNameInput
                )
            }

            UpsertIncomeAction.OnIncomeSaved -> {
                viewModelScope.launch {
                    if(saveIncome()){
                        _upsertBalanceEventChannel.send(UpsertIncomeEvents.UpsertIncomeSuccess)
                        localRepository.updateBalance(state.tempBalance)
                    }else{
                        _upsertBalanceEventChannel.send(UpsertIncomeEvents.UpsertIncomeFailed)
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