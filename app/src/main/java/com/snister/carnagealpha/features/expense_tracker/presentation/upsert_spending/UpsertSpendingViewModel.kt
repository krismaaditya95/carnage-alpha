package com.snister.carnagealpha.features.expense_tracker.presentation.upsert_spending

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snister.carnagealpha.features.expense_tracker.domain.entities.SpendingEntity
import com.snister.carnagealpha.features.expense_tracker.domain.repository.LocalRepository
import com.snister.carnagealpha.features.expense_tracker.domain.usecases.UpsertSpendingUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class UpsertSpendingViewModel(
    private val localRepository: LocalRepository,
    private val upsertSpendingUseCase: UpsertSpendingUseCase
) : ViewModel() {

    var state by mutableStateOf(UpsertSpendingState())
        private set

    private val _upsertSpendingEventChannel = Channel<UpsertSpendingEvents>()
    val event = _upsertSpendingEventChannel.receiveAsFlow()

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
                    spendingAmountInput = action.newSpendingAmountInput
                )
            }

            is UpsertSpendingAction.OnSpendingNameChanged -> {
                state = state.copy(
                    spendingNameInput = action.newSpendingNameInput
                )
            }

            UpsertSpendingAction.OnSpendingSaved -> {
                viewModelScope.launch {
                    // insert spending to database
                    if(saveSpending()){
                        _upsertSpendingEventChannel.send(UpsertSpendingEvents.UpsertSpendingSuccess)
                        localRepository.updateBalance(state.tempBalance)
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
            sourceLedgerId = 1
        )

        return upsertSpendingUseCase(spending)
    }
}