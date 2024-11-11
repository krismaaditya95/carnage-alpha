package com.snister.carnagealpha.features.expense_tracker.presentation.upsert_spending

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snister.carnagealpha.features.expense_tracker.domain.entities.SourceLedgerEntity
import com.snister.carnagealpha.features.expense_tracker.domain.entities.SpendingEntity
import com.snister.carnagealpha.features.expense_tracker.domain.repository.LocalRepository
import com.snister.carnagealpha.features.expense_tracker.domain.repository.SourceLedgerRepository
import com.snister.carnagealpha.features.expense_tracker.domain.usecases.GetSourceLedgerByIdUseCase
import com.snister.carnagealpha.features.expense_tracker.domain.usecases.UpsertSourceLedgerUseCase
import com.snister.carnagealpha.features.expense_tracker.domain.usecases.UpsertSpendingUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class UpsertSpendingViewModel(
    private val localRepository: LocalRepository,
    private val upsertSpendingUseCase: UpsertSpendingUseCase,
    private val getSourceLedgerByIdUseCase: GetSourceLedgerByIdUseCase,
    private val upsertSourceLedgerUseCase: UpsertSourceLedgerUseCase
) : ViewModel() {

    var state by mutableStateOf(UpsertSpendingState())
        private set

    private val _upsertSpendingEventChannel = Channel<UpsertSpendingEvents>()
    val event = _upsertSpendingEventChannel.receiveAsFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(
                currentActiveSourceLedgerId = localRepository.getCurrentSelectedSourceLedgerId()
            )
            state = state.copy(
//                initialBalance = localRepository.getBalance(),
//                tempBalance = localRepository.getBalance(),
                initialBalance = getSourceLedgerByIdUseCase(state.currentActiveSourceLedgerId).sourceLedgerBalance,
                tempBalance = getSourceLedgerByIdUseCase(state.currentActiveSourceLedgerId).sourceLedgerBalance,
                currentActiveSourceLedgerName = getSourceLedgerByIdUseCase(state.currentActiveSourceLedgerId).sourceLedgerName,
            )
        }
    }

    fun onAction(action: UpsertSpendingAction){
        when(action){
            is UpsertSpendingAction.OnSpendingAmountChanged -> {
                state = state.copy(
                    tempBalance = (state.initialBalance - (action.newSpendingAmountInput.toLongOrNull() ?: 0)),
                    spendingAmountInput = action.newSpendingAmountInput
                )
            }

            is UpsertSpendingAction.OnSpendingNameChanged -> {
                state = state.copy(
                    spendingNameInput = action.newSpendingNameInput
                )
            }

            UpsertSpendingAction.OnSpendingSaved -> {
                viewModelScope.launch(Dispatchers.IO) {
                    // insert spending to database
                    if(saveSpending() && updateSourceLedgerBalance(state.tempBalance)){
                        _upsertSpendingEventChannel.send(UpsertSpendingEvents.UpsertSpendingSuccess)
//                        localRepository.updateBalance(state.tempBalance)
                    }else{
                        _upsertSpendingEventChannel.send(UpsertSpendingEvents.UpsertSpendingFailed)
                    }
                }
            }
        }
    }

    private suspend fun saveSpending(): Boolean{
        val spending = SpendingEntity(
            spendingId = null,
            spendingName = state.spendingNameInput,
            spendingAmount = state.spendingAmountInput.toLong(),
            dateTime = ZonedDateTime.now(),
            //sementara
            sourceLedgerId = state.currentActiveSourceLedgerId
        )

        return upsertSpendingUseCase(spending)
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