package com.snister.carnagealpha.features.expense_tracker.presentation.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snister.carnagealpha.core.presentation.shared.TopBar
import com.snister.carnagealpha.ui.theme.CarnageAlphaTheme
import com.snister.carnagealpha.ui.theme.cEEEEEE
import org.koin.androidx.compose.koinViewModel

@Composable
fun DashboardMainScreen(
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel = koinViewModel(),
    onSaveClick: () -> Unit
) {
    DashboardCoreScreen(
        state = viewModel.state,
        onAction = viewModel::onAction,
        onSaveClick = {
            viewModel.onAction(DashboardBalanceAction.OnBalanceSaved)
            onSaveClick()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardCoreScreen(
    state: DashboardBalanceState,
    onAction: (DashboardBalanceAction) -> Unit,
    onSaveClick: () -> Unit
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
                appBarTitle = "Update Saldo kamu"
            )
        }
    ){ padding ->
        Box {
            Column(
                modifier = Modifier.padding(padding)
            ) {
                Text(
                    text = "${state.balance}",
                    fontSize = 20.sp,
                    color = cEEEEEE
                )

                OutlinedTextField(
                    value = state.balance.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onValueChange = {
                        onAction(DashboardBalanceAction.OnBalanceChanged(
                            it.toDoubleOrNull() ?: 0.0
                        ))
                    },
                    label = {
                        Text(
                            text = "Enter balance"
                        )
                    },
                    textStyle = TextStyle(
                        fontSize = 14.sp
                    ),
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )

                OutlinedButton(
                    modifier = Modifier.padding(start = 16.dp),
                    onClick = {
                        onSaveClick()
                    }
                ) {
                    Text(
                        text = "SAVE"
                    )
                }
            }
        }


    }
}

@Preview
@Composable
fun DashboardCoreScreenPreview(modifier: Modifier = Modifier) {
    CarnageAlphaTheme {
        DashboardCoreScreen(
            state = DashboardBalanceState(),
            onAction = {  },
            onSaveClick = {

            }
        )
    }
}