package com.snister.carnagealpha.features.expense_tracker.presentation.shared_widgets

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.snister.carnagealpha.features.expense_tracker.presentation.income_overview.IncomeOverviewAction
import com.snister.carnagealpha.features.expense_tracker.presentation.income_overview.IncomeOverviewState
import com.snister.carnagealpha.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun IncomeDatePickerWidget(
    modifier: Modifier = Modifier,
    state: IncomeOverviewState,
    onAction: (IncomeOverviewAction) -> Unit
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
//            value = state.selectedDateFromDatePicker,
            value = state.selectedDateRangeFromDateRangePicker,
            onValueChange = {

            },
            label = {
                Text(text = "Select date")
            },
            readOnly = true,
            trailingIcon = {
                IconButton(
                    onClick = {
                        onAction(IncomeOverviewAction.ShowIncomeDatePicker)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date")
                }
            },
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 22.dp, end = 22.dp)
                .height(60.dp)
                //doesnt work using clickable
//                .clickable (enabled = true){
//                    onAction(SpendingOverviewAction.ShowDatePicker)
//                },
                .pointerInput(
//                    state.selectedDateFromDatePicker
                    state.selectedDateRangeFromDateRangePicker
                ) {
                    awaitEachGesture {
                        awaitFirstDown(pass = PointerEventPass.Initial)
                        val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                        if (upEvent != null) {
                            onAction(IncomeOverviewAction.ShowIncomeDatePicker)
                        }
                    }
                },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = cmykGreen,
                focusedBorderColor = cmykGreen
            )
        )

        if(state.isDatePickerVisible){
            BottomSheetIncomeDatePickerWidget(
                state = state,
                onAction = onAction
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetIncomeDatePickerWidget(
    modifier: Modifier = Modifier,
    state: IncomeOverviewState,
    onAction: (IncomeOverviewAction) -> Unit,
) {
    val datePickerState = rememberDatePickerState()
    val datePickerRangeState = rememberDateRangePickerState()

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val sheetScope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = {
            sheetScope.launch {
                sheetState.hide()
            }.invokeOnCompletion {
                if(!sheetState.isVisible){
                    onAction(IncomeOverviewAction.HideIncomeDatePicker)
                }
            }
        },
        sheetState = sheetState,
    ) {
        Column {
//            DatePicker(
//                state = datePickerState,
//                showModeToggle = false
//            )

            DateRangePicker(
                modifier = modifier.fillMaxHeight(0.6f),
                state = datePickerRangeState
            )
            Row(
                modifier = modifier.align(Alignment.End),
            ) {
                TextButton(
                    onClick = {
                        sheetScope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            if(!sheetState.isVisible){
                                onAction(IncomeOverviewAction.HideIncomeDatePicker)
                            }
                        }
                    }
                ) {
                    Text(text = "CANCEL")
                }

                TextButton(
                    onClick = {
//                        datePickerState.selectedDateMillis?.let {
//                            onAction(
//                                IncomeOverviewAction.OnIncomeDateChange(
//                                    selectedDate = it
//                                )
//                            )
//                        }

                        onAction(
                            IncomeOverviewAction.OnIncomeDateRangePickerChange(
                                selectedDateRange = Pair(
                                    datePickerRangeState.selectedStartDateMillis,
                                    datePickerRangeState.selectedEndDateMillis
                                )
                            )
                        )

                        sheetScope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            if(!sheetState.isVisible){
                                onAction(IncomeOverviewAction.HideIncomeDatePicker)
                            }
                        }
                    }
                ) {
                    Text(text = "OK")
                }
            }
        }
    }

}