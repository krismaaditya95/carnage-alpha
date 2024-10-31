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
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class IncomeOverviewViewModel(
    private val incomeDataRepository: IncomeDataRepository,
    private val localRepository: LocalRepository
) : ViewModel(){

    var state by mutableStateOf(IncomeOverviewState())
        private set

    fun onAction(action: IncomeOverviewAction){
        when (action){
            IncomeOverviewAction.LoadIncomeOverviewAndBalance -> loadIncomeListAndBalance()
            is IncomeOverviewAction.OnIncomeDateChange -> onDatePickerSelected(action.selectedDate)
            is IncomeOverviewAction.OnDeleteIncome -> TODO()
            IncomeOverviewAction.ShowIncomeDatePicker -> showDatePicker()
            IncomeOverviewAction.HideIncomeDatePicker -> hideDatePicker()
            is IncomeOverviewAction.OnIncomeDateRangePickerChange -> onIncomeDateRangePickerChange(action.selectedDateRange)
        }
    }

    val dummyIncomeList = listOf(
        IncomeEntity(
            incomeId = 1,
            incomeAmount = 4000000,
            incomeSourceName = "Gaji bulan ini",
            dateTime = ZonedDateTime.now()
        ),
        IncomeEntity(
            incomeId = 1,
            incomeAmount = 1000000,
            incomeSourceName = "Dikasih tante",
            dateTime = ZonedDateTime.now()
        ),
        IncomeEntity(
            incomeId = 1,
            incomeAmount = 500000,
            incomeSourceName = "Ditransfer kakak",
            dateTime = ZonedDateTime.now()
        ),
    )
    private fun loadIncomeListAndBalance(){
        viewModelScope.launch {
            val allDates = incomeDataRepository.getAllDates()
            state = state.copy(

//                incomeList = dummyIncomeList,
                incomeList = getIncomeListByDate(ZonedDateTime.now()),
                balance = localRepository.getBalance(), /*incomeDataRepository.getTotalIncome(),*/
                pickedDate = ZonedDateTime.now(),
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
                selectedDateFromDatePicker = convertMillisToZonedDateTimeString(millis),
                pickedDate = convertMillisToZonedDateTime(millis),
                incomeList = getIncomeListByDate(convertMillisToZonedDateTime(millis))
            )
        }
    }

    private fun onIncomeDateRangePickerChange(selectedDateRange: Pair<Long?, Long?>){
        viewModelScope.launch {
            state = state.copy(
                selectedDateRangeFromDateRangePicker = convertDateRangeToZonedDateTimeString(selectedDateRange),
                pickedDateRange = convertDateRangeToZonedDateTime(selectedDateRange),
                incomeList = getIncomeListByDateRange(convertDateRangeToZonedDateTime(selectedDateRange))
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

    private fun convertDateRangeToZonedDateTime(selectedDateRange: Pair<Long?, Long?>): Pair<ZonedDateTime, ZonedDateTime>{
        val startZonedDateTime = ZonedDateTime.ofInstant(
            selectedDateRange.first?.let { Instant.ofEpochMilli(it) }, ZoneId.systemDefault())

        val endZonedDateTime = ZonedDateTime.ofInstant(
            selectedDateRange.second?.let { Instant.ofEpochMilli(it) }, ZoneId.systemDefault())

        return Pair(startZonedDateTime, endZonedDateTime)
    }

    private fun convertDateRangeToZonedDateTimeString(selectedDateRange: Pair<Long?, Long?>): String{
        val startZonedDateTime = ZonedDateTime.ofInstant(
            selectedDateRange.first?.let { Instant.ofEpochMilli(it) }, ZoneId.systemDefault())

        val endZonedDateTime = ZonedDateTime.ofInstant(
            selectedDateRange.second?.let { Instant.ofEpochMilli(it) }, ZoneId.systemDefault())

        val formattedStartZonedDateTime = DateTimeFormatter
            .ofPattern("dd MMM yyyy").format(startZonedDateTime)

        val formattedEndZonedDateTime = DateTimeFormatter
            .ofPattern("dd MMM yyyy").format(endZonedDateTime)

        return "$formattedStartZonedDateTime - $formattedEndZonedDateTime"
    }

    private suspend fun getIncomeListByDate(date: ZonedDateTime): List<IncomeEntity>{
        return incomeDataRepository
            .getIncomesByDate(date)
            .reversed()
    }

    private suspend fun getIncomeListByDateRange(dateRange: Pair<ZonedDateTime, ZonedDateTime>): List<IncomeEntity>{
        return incomeDataRepository
            .getIncomesByDateRange(dateRange)
            .reversed()
    }
}