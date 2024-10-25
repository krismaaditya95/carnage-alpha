package com.snister.carnagealpha.features.expense_tracker.presentation.spending_overview

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snister.carnagealpha.R
import com.snister.carnagealpha.core.presentation.shared.SpendingHighlights
import com.snister.carnagealpha.core.presentation.shared.TopBar
import com.snister.carnagealpha.features.expense_tracker.presentation.shared_widgets.DatePickerWidget
import com.snister.carnagealpha.features.expense_tracker.presentation.shared_widgets.MinimizedBalanceCard
import com.snister.carnagealpha.ui.theme.c151515
import com.snister.carnagealpha.ui.theme.cC73659
import com.snister.carnagealpha.ui.theme.cEEEEEE
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
        },
        bottomBar = {
            BottomAppBar(
                containerColor = cEEEEEE
            ){
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(cEEEEEE)
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column {
                        Text(
                            text = "Total Spendings - [datehere]",
                            color = c151515,
                            fontFamily = Font(R.font.roboto_regular).toFontFamily(),
                            fontSize = 12.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = modifier
                                .padding(bottom = 4.dp)
                        )

                        Text(
                            text = "Rp. 100,000",
                            color = cC73659,
                            fontFamily = Font(R.font.roboto_regular).toFontFamily(),
                            fontSize = 16.sp
                        )
                    }

                    FloatingActionButton(
                        onClick = {

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
//            Spacer(modifier = Modifier.height(4.dp))
            MinimizedBalanceCard(
                modifier = Modifier.fillMaxWidth(),
                onBalanceClick = onBalanceClick,
                balance = state.balance
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