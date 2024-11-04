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
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

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

    val dummySpendingList = listOf(
        SpendingEntity(
            spendingId = 1,
            dateTime = ZonedDateTime.now(),
            spendingName = "Beli Seblak",
            spendingAmount = 10000,
            sourceLedgerId = 1
        ),
        SpendingEntity(
            spendingId = 1,
            dateTime = ZonedDateTime.now(),
            spendingName = "Beli martabak",
            spendingAmount = 30000,
            sourceLedgerId = 1
        ),
        SpendingEntity(
            spendingId = 1,
            dateTime = ZonedDateTime.now(),
            spendingName = "Beli Terang bulan",
            spendingAmount = 23000,
            sourceLedgerId = 1
        ),
        SpendingEntity(
            spendingId = 1,
            dateTime = ZonedDateTime.now(),
            spendingName = "Beli Terang bulan",
            spendingAmount = 23000,
            sourceLedgerId = 1
        ),
        SpendingEntity(
            spendingId = 1,
            dateTime = ZonedDateTime.now(),
            spendingName = "Beli Terang bulan",
            spendingAmount = 23000,
            sourceLedgerId = 1
        ),
        SpendingEntity(
            spendingId = 1,
            dateTime = ZonedDateTime.now(),
            spendingName = "Beli Terang bulan",
            spendingAmount = 23000,
            sourceLedgerId = 1
        ),
        SpendingEntity(
            spendingId = 1,
            dateTime = ZonedDateTime.now(),
            spendingName = "Beli Terang bulan",
            spendingAmount = 23000,
            sourceLedgerId = 1
        ),
        SpendingEntity(
            spendingId = 1,
            dateTime = ZonedDateTime.now(),
            spendingName = "Beli Terang bulan",
            spendingAmount = 23000,
            sourceLedgerId = 1
        ),
        SpendingEntity(
            spendingId = 1,
            dateTime = ZonedDateTime.now(),
            spendingName = "Beli Terang bulan",
            spendingAmount = 23000,
            sourceLedgerId = 1
        ),
        SpendingEntity(
            spendingId = 1,
            dateTime = ZonedDateTime.now(),
            spendingName = "Beli Terang bulan",
            spendingAmount = 23000,
            sourceLedgerId = 1
        )
    )
    private fun loadSpendingListAndBalance(){
        viewModelScope.launch {
            val allDates = spendingDataRepository.getAllDates()
            state = state.copy(
                // nanti ini diuncomment
//                spendingList = getSpendingListByDate(
//                    allDates.lastOrNull() ?: ZonedDateTime.now()
//                ),
                spendingList = getSpendingListByDate(ZonedDateTime.now()),
                // sementara pakai data dummy
//                spendingList = dummySpendingList,
                balance = localRepository.getBalance(),

//                pickedDate = allDates.lastOrNull() ?: ZonedDateTime.now(),
                pickedDate = ZonedDateTime.now(),
                datesList = allDates.reversed()
            )

            state = state.copy(
                totalSpendingByDate = getTotalSpendByDate()
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
            val millisToZonedDateTime = convertMillisToZonedDateTime(millis)
            state = state.copy(
                selectedDateFromDatePicker = convertMillisToZonedDateTimeString(millis),
                pickedDate = millisToZonedDateTime,
                spendingList = getSpendingListByDate(millisToZonedDateTime)
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

    private suspend fun getSpendingListByDate(date: ZonedDateTime): List<SpendingEntity>{
        return spendingDataRepository
            .getSpendingsByDate(date)
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
        viewModelScope.launch {
            state = state.copy(
                spendingList = getSpendingListByDate(state.pickedDate),
                balance = localRepository.getBalance() - spendingDataRepository.getTotalSpend(),
            )
        }
    }
}