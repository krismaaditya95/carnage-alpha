package com.snister.carnagealpha.features.expense_tracker.presentation.income_overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snister.carnagealpha.features.expense_tracker.domain.entities.IncomeEntity
import com.snister.carnagealpha.features.expense_tracker.domain.repository.LocalRepository
import com.snister.carnagealpha.features.expense_tracker.domain.usecases.GetIncomeByDateUseCase
import com.snister.carnagealpha.features.expense_tracker.domain.usecases.GetSourceLedgerByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class IncomeOverviewViewModel(
    private val localRepository: LocalRepository,
    private val getIncomeByDateUseCase: GetIncomeByDateUseCase,
    private val getSourceLedgerByIdUseCase: GetSourceLedgerByIdUseCase
) : ViewModel(){

    var state by mutableStateOf(IncomeOverviewState())
        private set

    fun onAction(action: IncomeOverviewAction){
        when (action){
            IncomeOverviewAction.LoadIncomeOverviewAndBalance -> loadIncomeListAndBalance()
//            is IncomeOverviewAction.OnIncomeDateChange -> onDatePickerSelected(action.selectedDate)
            is IncomeOverviewAction.OnDeleteIncome -> TODO()
            IncomeOverviewAction.ShowIncomeDatePicker -> showDatePicker()
            IncomeOverviewAction.HideIncomeDatePicker -> hideDatePicker()
            is IncomeOverviewAction.OnIncomeDateRangePickerChange -> onIncomeDateRangePickerChange(action.selectedDateRange)
        }
    }

    private fun loadIncomeListAndBalance(){
        viewModelScope.launch(Dispatchers.IO) {
//            val allDates = incomeDataRepository.getAllDates()
            val defaultStartDate = ZonedDateTime.of(
                ZonedDateTime.now().year,
                ZonedDateTime.now().month.value,
                1, 0, 0, 0, 0, ZoneId.systemDefault())

            val defaultEndDate = ZonedDateTime.now()

            state = state.copy(
                currentActiveSourceLedgerId = localRepository.getCurrentSelectedSourceLedgerId()
            )
            state = state.copy(

                selectedDateRangeFromDateRangePicker = convertZonedDateTimeRangeToString(Pair(defaultStartDate, defaultEndDate)),
                incomeList = getIncomeListByDateRange(Pair(defaultStartDate, defaultEndDate), state.currentActiveSourceLedgerId),
//                datesList = allDates.reversed(),
                // balance yang disimpan di shared preferences, diganti dengan
//                balance = localRepository.getBalance(),
                // -> diganti dengan current source ledger
                currentSourceLedger = getSourceLedgerByIdUseCase(state.currentActiveSourceLedgerId),

            )

            state = state.copy(
                totalIncomesBySelectedDateRange = getTotalIncomesBySelectedDateRange()
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

//    private fun onDatePickerSelected(millis: Long){
//        viewModelScope.launch {
//            state = state.copy(
//                selectedDateFromDatePicker = convertMillisToZonedDateTimeString(millis),
//                pickedDate = convertMillisToZonedDateTime(millis),
//                incomeList = getIncomeListByDate(convertMillisToZonedDateTime(millis))
//            )
//        }
//    }

    private fun onIncomeDateRangePickerChange(selectedDateRange: Pair<Long?, Long?>){
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(
                selectedDateRangeFromDateRangePicker = convertDateRangeToZonedDateTimeString(selectedDateRange),
                pickedDateRange = convertDateRangeToZonedDateTime(selectedDateRange),
                incomeList = getIncomeListByDateRange(convertDateRangeToZonedDateTime(selectedDateRange), state.currentActiveSourceLedgerId)
            )
            state = state.copy(
                totalIncomesBySelectedDateRange = getTotalIncomesBySelectedDateRange()
            )
        }
    }

    // ---------------------------------
    // Conversion for normal date picker
    // ---------------------------------
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

    // ---------------------------------
    // Conversion for date range picker
    // ---------------------------------
    private fun convertZonedDateTimeRangeToString(
        selectedDateRangeInZonedDateTimeFormat: Pair<ZonedDateTime, ZonedDateTime>
    ): String{
        val formattedStartZonedDateTime = DateTimeFormatter
            .ofPattern("dd MMM yyyy").format(selectedDateRangeInZonedDateTimeFormat.first)

        val formattedEndZonedDateTime = DateTimeFormatter
            .ofPattern("dd MMM yyyy").format(selectedDateRangeInZonedDateTimeFormat.second)

        return "$formattedStartZonedDateTime - $formattedEndZonedDateTime"
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

//    private suspend fun getIncomeListByDate(date: ZonedDateTime): List<IncomeEntity>{
//        return incomeDataRepository
//            .getIncomesByDate(date)
//            .reversed()
//    }

    private suspend fun getIncomeListByDateRange(dateRange: Pair<ZonedDateTime, ZonedDateTime>, sourceLedgerId: Int): List<IncomeEntity>{
//        return incomeDataRepository
//            .getIncomesByDateRange(dateRange, sourceLedgerId)
//            .reversed()

        return getIncomeByDateUseCase(dateRange, sourceLedgerId)
    }

    private fun getTotalIncomesBySelectedDateRange(): Long{
        var totalIncomesBySelectedDateRange: Long = 0

        for (item in state.incomeList){
            totalIncomesBySelectedDateRange += item.incomeAmount
        }

        return totalIncomesBySelectedDateRange
    }
}