package com.snister.carnagealpha.features.expense_tracker.presentation.dashboard_overview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.snister.carnagealpha.core.presentation.shared.BalanceCardV3
import com.snister.carnagealpha.core.presentation.shared.MainMenuv2
import com.snister.carnagealpha.core.presentation.shared.TopBar
import com.snister.carnagealpha.core.presentation.shared.TransactionItemWidget
import com.snister.carnagealpha.core.presentation.shared.TransactionType

@Composable
fun DashboardOverviewScreen(
    viewModel: DashboardOverviewViewModel,
    onBalanceClick: () -> Unit,
    onAddSpendingClick: () -> Unit,
    onIncomeOverviewClick: () -> Unit,
    onChangeSourceLedgerClick: () -> Unit
) {
    LaunchedEffect(key1 = true) {
        viewModel.onAction(DashboardOverviewAction.LoadSpendingOverviewAndBalance)
        //viewModel.onAction(DashboardOverviewAction.LoadSourceLedgerList)
        //viewModel.onAction(DashboardOverviewAction.LoadSourceLedgerById)
    }
    
    DashboardOverviewCoreScreen(
        state = viewModel.state,
        onAction = viewModel::onAction,
        onBalanceClick = onBalanceClick,
        onAddSpendingClick = onAddSpendingClick,
        onIncomeOverviewClick = onIncomeOverviewClick,
        onDeleteSpending = {
            viewModel.onAction(DashboardOverviewAction.OnDeleteSpending(it))
        },
        onChangeSourceLedgerClick = onChangeSourceLedgerClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardOverviewCoreScreen(
    modifier: Modifier = Modifier,
    state: DashboardOverviewState,
    onAction: (DashboardOverviewAction) -> Unit,
    onBalanceClick: () -> Unit,
    onAddSpendingClick: () -> Unit,
    onIncomeOverviewClick: () -> Unit,
    onDeleteSpending: (Int) -> Unit,
    onChangeSourceLedgerClick: () -> Unit
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
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
//                .border(1.dp, cmykGreen)
        ){
            BalanceCardV3(
//                modifier = Modifier.fillMaxWidth(),
                onBalanceClick = onBalanceClick,
                balance = state.currentSourceLedger.sourceLedgerBalance,
                sourceLedgerName = state.currentSourceLedger.sourceLedgerName,
                onChangeSourceLedgerClick = onChangeSourceLedgerClick
            )
            Spacer(modifier = Modifier.height(4.dp))
            MainMenuv2(
                modifier = Modifier.fillMaxWidth(),
                onAddSpendingClick = {onAddSpendingClick()},
                onAddIncomeClick = {onIncomeOverviewClick()},
                onOtherClick = {}
            )
            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn(
                modifier = modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .padding(start = 20.dp, end = 20.dp),
//                    .border(1.dp, cmykRed),
                contentPadding = PaddingValues(top = 14.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                itemsIndexed(state.incomeList){
                        index, item ->
                    TransactionItemWidget(
                        transactionType = TransactionType.Income,
                        sourceName = item.incomeSourceName,
                        transactionTimeStamp = item.dateTime,
                        nominal = item.incomeAmount,
                        onDeleteIncome = { }
                    )
                }
            }
//            SpendingHighlights(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(20.dp)
//            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DashboardOverviewScreenPreview(modifier: Modifier = Modifier) {
    DashboardOverviewCoreScreen(
        state = DashboardOverviewState(),
        onAction = {},
        onBalanceClick = {},
        onAddSpendingClick = {},
        onIncomeOverviewClick = {},
        onDeleteSpending = {},
        onChangeSourceLedgerClick = {}
    )
}