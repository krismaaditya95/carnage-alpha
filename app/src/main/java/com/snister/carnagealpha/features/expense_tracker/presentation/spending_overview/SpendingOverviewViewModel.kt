package com.snister.carnagealpha.features.expense_tracker.presentation.spending_overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snister.carnagealpha.features.expense_tracker.domain.entities.SpendingEntity
import com.snister.carnagealpha.features.expense_tracker.domain.repository.LocalRepository
import com.snister.carnagealpha.features.expense_tracker.domain.repository.SourceLedgerRepository
import com.snister.carnagealpha.features.expense_tracker.domain.repository.SpendingDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class SpendingOverviewViewModel(
    private val spendingDataRepository: SpendingDataRepository,
    private val localRepository: LocalRepository,
    private val sourceLedgerRepository: SourceLedgerRepository
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
            SpendingOverviewAction.DisableAddButton -> disableAddButton()
        }
    }


    private fun loadSpendingListAndBalance(){
        viewModelScope.launch(Dispatchers.IO) {
            val allDates = spendingDataRepository.getAllDates()

            state = state.copy(
                currentActiveSourceLedgerId = localRepository.getCurrentSelectedSourceLedgerId()
            )

            state = state.copy(

                spendingList = getSpendingListByDate(ZonedDateTime.now(), state.currentActiveSourceLedgerId),

                // balance yang disimpan di shared preferences, diganti dengan
                balance = localRepository.getBalance(),
                // -> diganti dengan current source ledger
                currentSourceLedger = sourceLedgerRepository.getSourceLedgerById(
                    state.currentActiveSourceLedgerId
                ),

                pickedDate = ZonedDateTime.now(),
                datesList = allDates.reversed()
            )

            state = state.copy(
                totalSpendingByDate = getTotalSpendByDate()
            )

            state = state.copy(
                disableButton = false
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

    private fun disableAddButton(){
        viewModelScope.launch {
            state = state.copy(
                disableButton = true
            )
        }
    }

    private fun onDatePickerSelected(millis: Long){
        viewModelScope.launch(Dispatchers.IO) {
            val millisToZonedDateTime = convertMillisToZonedDateTime(millis)
            state = state.copy(
                selectedDateFromDatePicker = convertMillisToZonedDateTimeString(millis),
                pickedDate = millisToZonedDateTime,
                spendingList = getSpendingListByDate(millisToZonedDateTime, state.currentActiveSourceLedgerId)
            )
            // calculate total spending from state.spendingList
            state = state.copy(
                totalSpendingByDate = getTotalSpendByDate()
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

    private suspend fun getSpendingListByDate(
        date: ZonedDateTime,
        sourceLedgerId: Int
    ): List<SpendingEntity>{
        return spendingDataRepository
            .getSpendingsByDate(date, sourceLedgerId)
            .reversed()
    }

    private fun getTotalSpendByDate(): Long{
        var totalSpendByDate: Long = 0

        for (item in state.spendingList){
            totalSpendByDate += item.spendingAmount
        }

        return totalSpendByDate
    }

    private suspend fun deleteSpendingById(id: Int){
        spendingDataRepository.deleteSpending(id)
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(
                spendingList = getSpendingListByDate(state.pickedDate, state.currentActiveSourceLedgerId),
                balance = localRepository.getBalance() - spendingDataRepository.getTotalSpend(),
            )
        }
    }
}