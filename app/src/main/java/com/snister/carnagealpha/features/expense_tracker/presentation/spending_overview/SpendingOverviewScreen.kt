package com.snister.carnagealpha.features.expense_tracker.presentation.spending_overview

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snister.carnagealpha.R
import com.snister.carnagealpha.core.presentation.shared.TopBar
import com.snister.carnagealpha.core.utils.CurrencyFormatter
import com.snister.carnagealpha.features.expense_tracker.presentation.shared_widgets.DatePickerWidget
import com.snister.carnagealpha.features.expense_tracker.presentation.shared_widgets.MinimizedBalanceCard
import com.snister.carnagealpha.ui.theme.*
import org.koin.androidx.compose.koinViewModel
import java.time.ZoneId
import java.time.ZonedDateTime

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
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .padding(bottom = 50.dp),
        topBar = {
            TopBar(
                scrollBehavior = scrollBehavior,
                modifier = Modifier.fillMaxWidth(),
                appBarTitle = "Your spendings",
                navigationIcon = true
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = modifier
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
//                        .background(cEEEEEE)
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column {
                        Text(
                            text = "Total Spendings - ${state.selectedDateFromDatePicker}",
//                            color = c151515,
                            fontFamily = Font(R.font.roboto_regular).toFontFamily(),
                            fontSize = 12.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = modifier
                                .padding(bottom = 4.dp)
                        )

                        Text(
                            text = CurrencyFormatter.formatToRupiah(state.totalSpendingByDate),
//                            text = "time : ${ZonedDateTime.now()}",
                            color = cC73659,
                            fontFamily = Font(R.font.roboto_regular).toFontFamily(),
                            fontSize = 16.sp
                        )
                    }

                    FloatingActionButton(
                        onClick = {
                            onAddSpendingClick()
                        },
                        containerColor = cC73659,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            tint = cEEEEEE,
                            contentDescription = "Add spending"
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .fillMaxSize()
        ){
            MinimizedBalanceCard(
                onBalanceClick = onBalanceClick,
                balance = state.balance,
                onChangeSourceLedgerClick = {}
            )

            DatePickerWidget(
                onAction = onAction,
                state = state,
                modifier = modifier
            )

            Spacer(modifier = Modifier.height(10.dp))

            SpendingListWidget(
                modifier = modifier,
                state = state,
                onDeleteSpending = onDeleteSpending
            )
        }
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