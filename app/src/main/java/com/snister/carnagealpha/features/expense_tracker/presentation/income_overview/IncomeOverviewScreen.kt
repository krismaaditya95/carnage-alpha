package com.snister.carnagealpha.features.expense_tracker.presentation.income_overview

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
import com.snister.carnagealpha.features.expense_tracker.presentation.shared_widgets.IncomeDatePickerWidget
import com.snister.carnagealpha.features.expense_tracker.presentation.shared_widgets.MinimizedBalanceCard
import com.snister.carnagealpha.features.expense_tracker.presentation.income_overview.IncomeOverviewViewModel
import com.snister.carnagealpha.ui.theme.CarnageAlphaTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun IncomeOverviewScreen(
    viewModel: IncomeOverviewViewModel = koinViewModel(),
    onBalanceClick: () -> Unit,
    onAddIncomeClick: () -> Unit,
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
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncomeOverviewCoreScreen(
    state: IncomeOverviewState,
    onAction: (IncomeOverviewAction) -> Unit,
    onBalanceClick: () -> Unit,
    onAddIncomeClick: () -> Unit,
    onDeleteIncome: (Int) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = rememberTopAppBarState()
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBar(
                scrollBehavior = scrollBehavior,
                modifier = Modifier.fillMaxWidth(),
                appBarTitle = "Your Incomes",
                navigationIcon = true
            )
        }
    ){ padding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .fillMaxSize()
        ){
            MinimizedBalanceCard(
                modifier = Modifier.fillMaxWidth(),
                onBalanceClick = onBalanceClick,
                balance = state.balance
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
            onDeleteIncome = {}
        )
    }
}