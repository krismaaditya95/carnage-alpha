package com.snister.carnagealpha.features.expense_tracker.presentation.dashboard_overview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.snister.carnagealpha.core.presentation.shared.BalanceCard
import com.snister.carnagealpha.core.presentation.shared.MainMenu
import com.snister.carnagealpha.core.presentation.shared.SpendingHighlights
import com.snister.carnagealpha.core.presentation.shared.TopBar
import com.snister.carnagealpha.ui.theme.CarnageAlphaTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun DashboardOverviewScreen(
    modifier: Modifier = Modifier,
    viewModel: DashboardOverviewViewModel = koinViewModel(),
    onBalanceClick: () -> Unit,
    onAddSpendingClick: () -> Unit,
    onIncomeOverviewClick: () -> Unit
) {
    LaunchedEffect(key1 = true) {
        viewModel.onAction(DashboardOverviewAction.LoadSpendingOverviewAndBalance)
    }
    
    DashboardOverviewCoreScreen(
        state = viewModel.state,
        onAction = viewModel::onAction,
        onBalanceClick = onBalanceClick,
        onAddSpendingClick = onAddSpendingClick,
        onIncomeOverviewClick = onIncomeOverviewClick,
        onDeleteSpending = {
            viewModel.onAction(DashboardOverviewAction.OnDeleteSpending(it))
        }
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
            Spacer(modifier = Modifier.height(30.dp))
            BalanceCard(
                modifier = Modifier.fillMaxWidth(),
                onBalanceClick = onBalanceClick,
                balance = state.balance
            )
            Spacer(modifier = Modifier.height(4.dp))
            MainMenu(
                modifier = Modifier.fillMaxWidth(),
                onAddSpendingClick = {onAddSpendingClick()},
                onAddIncomeClick = {onIncomeOverviewClick()},
                onOtherClick = {}
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


@Preview(showBackground = true)
@Composable
fun DashboardOverviewScreenPreview(modifier: Modifier = Modifier) {
    DashboardOverviewCoreScreen(
        state = DashboardOverviewState(),
        onAction = {},
        onBalanceClick = {},
        onAddSpendingClick = {},
        onIncomeOverviewClick = {},
        onDeleteSpending = {}
    )
}