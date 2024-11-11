package com.snister.carnagealpha.features.expense_tracker.presentation.upsert_income

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snister.carnagealpha.features.expense_tracker.domain.entities.IncomeEntity
import com.snister.carnagealpha.features.expense_tracker.domain.entities.SourceLedgerEntity
import com.snister.carnagealpha.features.expense_tracker.domain.repository.LocalRepository
import com.snister.carnagealpha.features.expense_tracker.domain.usecases.GetSourceLedgerByIdUseCase
import com.snister.carnagealpha.features.expense_tracker.domain.usecases.UpsertIncomeUseCase
import com.snister.carnagealpha.features.expense_tracker.domain.usecases.UpsertSourceLedgerUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class UpsertIncomeViewModel(
    private val localRepository: LocalRepository,
    private val upsertIncomeUseCase: UpsertIncomeUseCase,
    private val getSourceLedgerByIdUseCase: GetSourceLedgerByIdUseCase,
    private val upsertSourceLedgerUseCase: UpsertSourceLedgerUseCase
) : ViewModel(){

    var state by mutableStateOf(UpsertIncomeState())
        private set

    private val _upsertBalanceEventChannel = Channel<UpsertIncomeEvents>()
    val event = _upsertBalanceEventChannel.receiveAsFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(
                currentActiveSourceLedgerId = localRepository.getCurrentSelectedSourceLedgerId()
            )

            state = state.copy(
//                initialBalance = localRepository.getBalance(),
//                tempBalance = localRepository.getBalance()
                initialBalance = getSourceLedgerByIdUseCase(state.currentActiveSourceLedgerId).sourceLedgerBalance,
                tempBalance = getSourceLedgerByIdUseCase(state.currentActiveSourceLedgerId).sourceLedgerBalance,
                currentActiveSourceLedgerName = getSourceLedgerByIdUseCase(state.currentActiveSourceLedgerId).sourceLedgerName,
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
                viewModelScope.launch(Dispatchers.IO) {
                    if(saveIncome() && updateSourceLedgerBalance(state.tempBalance)){
                        _upsertBalanceEventChannel.send(UpsertIncomeEvents.UpsertIncomeSuccess)
//                        localRepository.updateBalance(state.tempBalance)
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
            dateTime = ZonedDateTime.now(),
            sourceLedgerId = state.currentActiveSourceLedgerId
        )

        return upsertIncomeUseCase(income)
    }

    private suspend fun updateSourceLedgerBalance(updatedBalance: Long): Boolean{
        val updatedSourceLedger = SourceLedgerEntity(
            sourceLedgerId = state.currentActiveSourceLedgerId,
            sourceLedgerName = state.currentActiveSourceLedgerName,
            sourceLedgerBalance = updatedBalance
        )

        return upsertSourceLedgerUseCase(updatedSourceLedger)
    }
}