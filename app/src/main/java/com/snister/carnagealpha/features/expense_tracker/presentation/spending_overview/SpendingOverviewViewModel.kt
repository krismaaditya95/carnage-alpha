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
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class SpendingOverviewViewModel(
    private val spendingDataRepository: SpendingDataRepository,
    private val localRepository: LocalRepository
) : ViewModel(){

    var state by mutableStateOf(SpendingOverviewState())
        private set

    fun onAction(action: SpendingOverviewAction){
        when (action){

            SpendingOverviewAction.LoadSpendingOverviewAndBalance -> loadSpendingListAndBalance()
            is SpendingOverviewAction.OnDateChange -> onDatePickerSelected(action.selectedDate)
            is SpendingOverviewAction.OnDeleteSpending -> TODO()
            SpendingOverviewAction.ShowDatePicker -> showDatePicker()
            SpendingOverviewAction.HideDatePicker -> hideDatePicker()
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

    private fun showDatePicker(){
        viewModelScope.launch {
            state = state.copy(
                isDatePickerVisible = true
            )
        }
    }
    private fun hideDatePicker(){
        viewModelScope.launch {
            state = state.copy(
                isDatePickerVisible = false
            )
        }
    }

    private fun onDatePickerSelected(millis: Long){
        viewModelScope.launch {
            state = state.copy(
                selectedDateFromDatePicker = convertMillisToZonedDateTimeString(millis)
            )
        }
    }

    private fun convertMillisToZonedDateTime(millis: Long): ZonedDateTime{
        val formattedZonedDateTime = ZonedDateTime.ofInstant(
            Instant.ofEpochMilli(millis), ZoneId.systemDefault())

        return formattedZonedDateTime
    }

    private fun convertMillisToZonedDateTimeString(millis: Long): String{
        val zonedDateTime = ZonedDateTime.ofInstant(
            Instant.ofEpochMilli(millis), ZoneId.systemDefault())

        val formattedZonedDateTime = DateTimeFormatter
            .ofPattern("dd-MMMM-yyyy").format(zonedDateTime)
        return formattedZonedDateTime.toString()
    }

    private suspend fun getSpendingListByDate(date: ZonedDateTime): List<SpendingEntity>{
        return spendingDataRepository
            .getSpendingsByDate(date)
            .reversed()
    }
}