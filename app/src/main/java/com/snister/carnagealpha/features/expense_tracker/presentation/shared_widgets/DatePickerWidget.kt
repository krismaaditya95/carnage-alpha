package com.snister.carnagealpha.features.expense_tracker.presentation.shared_widgets

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.snister.carnagealpha.features.expense_tracker.presentation.spending_overview.SpendingOverviewAction
import com.snister.carnagealpha.features.expense_tracker.presentation.spending_overview.SpendingOverviewState
import com.snister.carnagealpha.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun DatePickerWidget(
    modifier: Modifier = Modifier,
    state: SpendingOverviewState,
    onAction: (SpendingOverviewAction) -> Unit
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = state.selectedDateFromDatePicker,
            onValueChange = {

            },
            label = {
                Text(text = "Select date")
            },
            readOnly = true,
            trailingIcon = {
                IconButton(
                    onClick = {
                        onAction(SpendingOverviewAction.ShowDatePicker)
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
                .pointerInput(state.selectedDateFromDatePicker) {
                    awaitEachGesture {
                        awaitFirstDown(pass = PointerEventPass.Initial)
                        val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                        if (upEvent != null) {
                            onAction(SpendingOverviewAction.ShowDatePicker)
                        }
                    }
                },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = cC73659,
                focusedBorderColor = cA91D3A
            )
        )

        if(state.isDatePickerVisible){
//            PopUpDatePickerWidget(
//                state = state,
//                onAction = onAction
//            )

//            ModalDatePickerWidget(
//                state = state,
//                onAction = onAction
//            )

            BottomSheetDatePickerWidget(
                state = state,
                onAction = onAction
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopUpDatePickerWidget(
    modifier: Modifier = Modifier,
    state: SpendingOverviewState,
    onAction: (SpendingOverviewAction) -> Unit,
) {
    val datePickerState = rememberDatePickerState()

    Popup(
        onDismissRequest = {onAction(SpendingOverviewAction.HideDatePicker)},
        alignment = Alignment.TopStart
    ) {
        Column {
            DatePicker(
                state = datePickerState,
                showModeToggle = false
            )
            TextButton(
                modifier = modifier.align(Alignment.End),
                onClick = {
                    datePickerState.selectedDateMillis?.let{
                        onAction(SpendingOverviewAction.OnDateChange(
                            selectedDate = it
                        ))
                    }

                    onAction(SpendingOverviewAction.HideDatePicker)
                }
            ) {
                Text(text = "OK")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalDatePickerWidget(
    modifier: Modifier = Modifier,
    state: SpendingOverviewState,
    onAction: (SpendingOverviewAction) -> Unit,
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = {
            onAction(SpendingOverviewAction.HideDatePicker)
        },
        confirmButton = {
            TextButton(
//                modifier = modifier.align(Alignment.End),
                onClick = {
                    datePickerState.selectedDateMillis?.let{
                        onAction(SpendingOverviewAction.OnDateChange(
                            selectedDate = it
                        ))
                    }

                    onAction(SpendingOverviewAction.HideDatePicker)
                }
            ) {
                Text(text = "OK")
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            showModeToggle = false
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetDatePickerWidget(
    modifier: Modifier = Modifier,
    state: SpendingOverviewState,
    onAction: (SpendingOverviewAction) -> Unit,
) {
    val datePickerState = rememberDatePickerState()
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
                    onAction(SpendingOverviewAction.HideDatePicker)
                }
            }
        },
        sheetState = sheetState,
    ) {
        Column {
            DatePicker(
                state = datePickerState,
                showModeToggle = false
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
                                onAction(SpendingOverviewAction.HideDatePicker)
                            }
                        }
                    }
                ) {
                    Text(text = "CANCEL")
                }

                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            onAction(
                                SpendingOverviewAction.OnDateChange(
                                    selectedDate = it
                                )
                            )
                        }

                        sheetScope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            if(!sheetState.isVisible){
                                onAction(SpendingOverviewAction.HideDatePicker)
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