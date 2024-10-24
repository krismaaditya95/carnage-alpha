package com.snister.carnagealpha.features.expense_tracker.presentation.spending_overview

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
import com.snister.carnagealpha.core.presentation.shared.SpendingHighlights
import com.snister.carnagealpha.core.presentation.shared.TopBar
import com.snister.carnagealpha.features.expense_tracker.presentation.shared_widgets.DatePickerWidget
import com.snister.carnagealpha.features.expense_tracker.presentation.shared_widgets.MinimizedBalanceCard
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
                modifier = Modifier.fillMaxWidth(),
                appBarTitle = "Your spendings",
                navigationIcon = true
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