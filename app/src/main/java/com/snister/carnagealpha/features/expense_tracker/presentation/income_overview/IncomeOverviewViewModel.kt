package com.snister.carnagealpha.features.expense_tracker.presentation.income_overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snister.carnagealpha.features.expense_tracker.domain.entities.IncomeEntity
import com.snister.carnagealpha.features.expense_tracker.domain.entities.SpendingEntity
import com.snister.carnagealpha.features.expense_tracker.domain.repository.IncomeDataRepository
import com.snister.carnagealpha.features.expense_tracker.domain.repository.LocalRepository
import com.snister.carnagealpha.features.expense_tracker.domain.repository.SpendingDataRepository
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class IncomeOverviewViewModel(
    private val incomeDataRepository: IncomeDataRepository,
    private val localRepository: LocalRepository
) : ViewModel(){

    var state by mutableStateOf(IncomeOverviewState())
        private set

    fun onAction(action: IncomeOverviewAction){
        when (action){
            IncomeOverviewAction.LoadIncomeOverviewAndBalance -> TODO()
            is IncomeOverviewAction.OnDateChange -> TODO()
            is IncomeOverviewAction.OnDeleteIncome -> TODO()
        }
    }

    private fun loadIncomeListAndBalance(){
        viewModelScope.launch {
            val allDates = incomeDataRepository.getAllDates()
            state = state.copy(
                incomeList = getIncomeListByDate(
                    allDates.lastOrNull() ?: ZonedDateTime.now()
                ),
                balance = localRepository.getBalance(), /*incomeDataRepository.getTotalIncome(),*/
                pickedDate = allDates.lastOrNull() ?: ZonedDateTime.now(),
                datesList = allDates.reversed()
            )
        }
    }

    private suspend fun getIncomeListByDate(date: ZonedDateTime): List<IncomeEntity>{
        return incomeDataRepository
            .getIncomesByDate(date)
            .reversed()
    }
}