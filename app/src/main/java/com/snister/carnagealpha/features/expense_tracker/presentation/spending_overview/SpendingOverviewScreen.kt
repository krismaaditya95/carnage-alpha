package com.snister.carnagealpha.features.expense_tracker.presentation.spending_overview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.snister.carnagealpha.core.presentation.shared.BalanceCard
import com.snister.carnagealpha.core.presentation.shared.MainMenu
import com.snister.carnagealpha.core.presentation.shared.SpendingHighlights
import com.snister.carnagealpha.core.presentation.shared.TopBar
import com.snister.carnagealpha.features.expense_tracker.presentation.shared_widgets.MinimizedBalanceCard
import com.snister.carnagealpha.features.expense_tracker.presentation.spending_overview.SpendingOverviewState
import com.snister.carnagealpha.features.expense_tracker.presentation.spending_overview.SpendingOverviewViewModel
import com.snister.carnagealpha.ui.theme.*
import org.koin.androidx.compose.koinViewModel

@Composable
fun SpendingOverviewScreen(
    modifier: Modifier = Modifier,
    viewModel: SpendingOverviewViewModel = koinViewModel(),
    onBalanceClick: () -> Unit,
    onAddSpendingClick: () -> Unit,
) {
    LaunchedEffect(key1 = true) {
        viewModel.onAction(SpendingOverviewAction.LoadSpendingOverviewAndBalance)
    }

    SpendingOverviewCoreScreen(
        state = viewModel.state,
        onAction = viewModel::onAction,
        onBalanceClick = onBalanceClick,
        onAddSpendingClick = onAddSpendingClick,
        onDeleteSpending = {
            viewModel.onAction(SpendingOverviewAction.OnDeleteSpending(it))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpendingOverviewCoreScreen(
    modifier: Modifier = Modifier,
    state: SpendingOverviewState,
    onAction: (SpendingOverviewAction) -> Unit,
    onBalanceClick: () -> Unit,
    onAddSpendingClick: () -> Unit,
    onDeleteSpending: (Int) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = rememberTopAppBarState()
    )
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBar(
                scrollBehavior = scrollBehavior,
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .fillMaxSize()
        ){
//            Spacer(modifier = Modifier.height(4.dp))
            MinimizedBalanceCard(
                modifier = Modifier.fillMaxWidth(),
                onBalanceClick = onBalanceClick,
                balance = state.balance
            )

            DatePickerWidget(
                onAction = onAction,
                state = state,
            )

            Spacer(modifier = Modifier.height(10.dp))
            SpendingHighlights(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerWidget(
    modifier: Modifier = Modifier,
    state: SpendingOverviewState,
    onAction: (SpendingOverviewAction) -> Unit
) {

    val datePickerState = rememberDatePickerState()

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
                .height(60.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = c151515,
                focusedBorderColor = cC73659
            )
        )
    }

}

@Preview(showBackground = true)
@Composable
fun SpendingOverviewScreenPreview(modifier: Modifier = Modifier) {
    SpendingOverviewCoreScreen(
        state = SpendingOverviewState(),
        onAction = {},
        onBalanceClick = {},
        onAddSpendingClick = {},
        onDeleteSpending = {}
    )
}