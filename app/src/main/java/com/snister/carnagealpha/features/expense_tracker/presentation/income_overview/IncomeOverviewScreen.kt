package com.snister.carnagealpha.features.expense_tracker.presentation.income_overview

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
import com.snister.carnagealpha.features.expense_tracker.presentation.shared_widgets.IncomeDatePickerWidget
import com.snister.carnagealpha.features.expense_tracker.presentation.shared_widgets.MinimizedBalanceCard
import com.snister.carnagealpha.ui.theme.CarnageAlphaTheme
import com.snister.carnagealpha.ui.theme.*
import org.koin.androidx.compose.koinViewModel

@Composable
fun IncomeOverviewScreen(
    viewModel: IncomeOverviewViewModel,
    onBalanceClick: () -> Unit,
    onAddIncomeClick: () -> Unit,
    onChangeSourceLedgerClick: () -> Unit
) {
    LaunchedEffect(key1 = true) {
        viewModel.onAction(IncomeOverviewAction.LoadIncomeOverviewAndBalance)
    }

    IncomeOverviewCoreScreen(
        state = viewModel.state,
        onAction = viewModel::onAction,
        onBalanceClick = onBalanceClick,
        onAddIncomeClick = onAddIncomeClick,
        onDeleteIncome = {
            viewModel.onAction(IncomeOverviewAction.OnDeleteIncome(it))
        },
        onChangeSourceLedgerClick = onChangeSourceLedgerClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncomeOverviewCoreScreen(
    state: IncomeOverviewState,
    onAction: (IncomeOverviewAction) -> Unit,
    onBalanceClick: () -> Unit,
    onAddIncomeClick: () -> Unit,
    onDeleteIncome: (Int) -> Unit,
    onChangeSourceLedgerClick: () -> Unit
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
                appBarTitle = "Your Incomes",
                navigationIcon = true
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column {
                        Text(
                            text = "Total Incomes - ${state.selectedDateRangeFromDateRangePicker}",
                            fontFamily = Font(R.font.roboto_regular).toFontFamily(),
                            fontSize = 12.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )

                        Text(
                            text = CurrencyFormatter.formatToRupiah(state.totalIncomesBySelectedDateRange),
                            color = cmykGreen,
                            fontFamily = Font(R.font.roboto_regular).toFontFamily(),
                            fontSize = 16.sp
                        )
                    }

                    FloatingActionButton(
                        onClick = {
                            onAddIncomeClick()
                        },
                        containerColor = cmykGreen,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            tint = cEEEEEE,
                            contentDescription = "Add Income"
                        )
                    }
                }
            }
        }
    ){ padding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .fillMaxSize()
        ){
            MinimizedBalanceCard(
                onBalanceClick = onBalanceClick,
                balance = state.currentSourceLedger.sourceLedgerBalance,
                sourceLedgerName = state.currentSourceLedger.sourceLedgerName,
                onChangeSourceLedgerClick = onChangeSourceLedgerClick
            )
            IncomeDatePickerWidget(
                onAction = onAction,
                state = state
            )

            Spacer(modifier = Modifier.height(10.dp))

            IncomeListWidget(
                state = state,
                onDeleteIncome = onDeleteIncome
            )

        }
    }
}

@Preview
@Composable
fun UpsertCoreScreenPreview(modifier: Modifier = Modifier) {
    CarnageAlphaTheme {
        IncomeOverviewCoreScreen(
            state = IncomeOverviewState(),
            onAction = {  },
            onBalanceClick = {},
            onAddIncomeClick = {},
            onDeleteIncome = {},
            onChangeSourceLedgerClick = {}
        )
    }
}