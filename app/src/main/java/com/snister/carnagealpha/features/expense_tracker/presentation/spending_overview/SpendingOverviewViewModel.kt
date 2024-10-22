package com.snister.carnagealpha.features.expense_tracker.presentation.spending_overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snister.carnagealpha.features.expense_tracker.domain.entities.SpendingEntity
import com.snister.carnagealpha.features.expense_tracker.domain.repository.LocalRepository
import com.snister.carnagealpha.features.expense_tracker.domain.repository.SpendingDataRepository
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class SpendingOverviewViewModel(
    private val spendingDataRepository: SpendingDataRepository,
    private val localRepository: LocalRepository
) : ViewModel(){

    var state by mutableStateOf(SpendingOverviewState())
        private set

    fun onAction(action: SpendingOverviewAction){
        when (action){

            SpendingOverviewAction.LoadSpendingOverviewAndBalance -> loadSpendingListAndBalance()
            is SpendingOverviewAction.OnDateChange -> TODO()
            is SpendingOverviewAction.OnDeleteSpending -> TODO()
        }
    }

    private fun loadSpendingListAndBalance(){
        viewModelScope.launch {
            val allDates = spendingDataRepository.getAllDates()
            state = state.copy(
                spendingList = getSpendingListByDate(
                    allDates.lastOrNull() ?: ZonedDateTime.now()
                ),
                balance = localRepository.getBalance() - spendingDataRepository.getTotalSpend(),
                pickedDate = allDates.lastOrNull() ?: ZonedDateTime.now(),
                datesList = allDates.reversed()
            )
        }
    }

    private suspend fun getSpendingListByDate(date: ZonedDateTime): List<SpendingEntity>{
        return spendingDataRepository
            .getSpendingsByDate(date)
            .reversed()
    }
}