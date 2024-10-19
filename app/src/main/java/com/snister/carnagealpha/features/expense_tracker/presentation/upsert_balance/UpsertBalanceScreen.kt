package com.snister.carnagealpha.features.expense_tracker.presentation.upsert_balance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snister.carnagealpha.core.presentation.shared.TopBar
import com.snister.carnagealpha.core.utils.CurrencyFormatter
import com.snister.carnagealpha.ui.theme.CarnageAlphaTheme
import com.snister.carnagealpha.ui.theme.*
import org.koin.androidx.compose.koinViewModel

@Composable
fun UpsertBalanceScreen(
    modifier: Modifier = Modifier,
    viewModel: UpsertBalanceViewModel = koinViewModel(),
    onSaveClick: () -> Unit
) {
    UpsertBalanceCoreScreen(
        state = viewModel.state,
        onAction = viewModel::onAction,
        onSaveClick = {
            viewModel.onAction(UpsertBalanceAction.OnBalanceSaved)
            onSaveClick()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpsertBalanceCoreScreen(
    state: UpsertBalanceState,
    onAction: (UpsertBalanceAction) -> Unit,
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
                Box {
                    Box(
                        modifier = Modifier
                            .padding(start = 16.dp, top = 16.dp)
                            .clip(CircleShape)
                            .width(200.dp)
                            .height(40.dp)
                            .background(cDC5F00)
                            .align(Alignment.CenterStart),
                    )

                    Text(
                        modifier = Modifier
                            .padding(start = 26.dp, top = 16.dp)
                            .align(Alignment.CenterStart),
                        text = CurrencyFormatter.formatToRupiah(state.balance),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = cEEEEEE,
                    )
                }

                OutlinedTextField(
                    value = state.income,
                    placeholder = {
                        Text(text = state.income)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    onValueChange = { newValue ->
                        onAction(UpsertBalanceAction.OnBalanceChanged(
                            newValue.toLongOrNull() ?: 0,
                            newIncome = newValue
                        ))

//                        onAction(UpsertBalanceAction.OnNewIncomeChanged(
//                            newValue.toLongOrNull() ?: 0,
//                        ))
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
                        keyboardType = KeyboardType.Decimal
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
fun UpsertCoreScreenPreview(modifier: Modifier = Modifier) {
    CarnageAlphaTheme {
        UpsertBalanceCoreScreen(
            state = UpsertBalanceState(),
            onAction = {  },
            onSaveClick = {

            }
        )
    }
}