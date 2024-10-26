package com.snister.carnagealpha.features.expense_tracker.presentation.upsert_spending

import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snister.carnagealpha.core.presentation.shared.TopBar
import com.snister.carnagealpha.core.utils.CurrencyFormatter
import com.snister.carnagealpha.features.expense_tracker.presentation.upsert_balance.UpsertBalanceAction
import com.snister.carnagealpha.ui.theme.cDC5F00
import com.snister.carnagealpha.ui.theme.cEEEEEE
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun UpsertSpendingScreen(
    viewModel: UpsertSpendingViewModel = koinViewModel(),
    onSaveClick: () -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.event.collect { event ->
            when(event){
                UpsertSpendingEvents.UpsertSpendingFailed -> {
                    Toast.makeText(
                        context, "ERROR", Toast.LENGTH_LONG
                    ).show()
                }
                UpsertSpendingEvents.UpsertSpendingSuccess -> {
                    Toast.makeText(
                        context, "SUCCESS", Toast.LENGTH_LONG
                    ).show()
                    onSaveClick()
                }
            }
        }
    }

    UpsertSpendingCoreScreen(
        state = viewModel.state,
        onAction = viewModel::onAction,
        onSaveClick = {
            viewModel.onAction(UpsertSpendingAction.OnSpendingSaved)
//            onSaveClick()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpsertSpendingCoreScreen(
    state: UpsertSpendingState,
    onAction: (UpsertSpendingAction) -> Unit,
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
                appBarTitle = "Tambah pengeluaran"
            )
        }
    ) { innerPadding ->
        Box {
            Column(
                modifier = Modifier.padding(innerPadding)
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
                        text = CurrencyFormatter.formatToRupiah(state.tempBalance),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = cEEEEEE,
                    )
                }

                OutlinedTextField(
                    value = state.spendingAmountInput,
                    placeholder = {
                        Text(text = state.spendingAmountInput)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    onValueChange = { newValue ->
                        onAction(UpsertSpendingAction.OnSpendingAmountChanged(newValue))
                    },
                    label = {
                        Text(
                            text = "Masukkan nominal pengeluaran"
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

                OutlinedTextField(
                    value = state.spendingNameInput,
                    placeholder = {
                        Text(text = state.spendingNameInput)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 2.dp),
                    onValueChange = { newValue ->
                        onAction(UpsertSpendingAction.OnSpendingNameChanged(newValue))
                    },
                    label = {
                        Text(
                            text = "Nama pengeluaran"
                        )
                    },
                    textStyle = TextStyle(
                        fontSize = 14.sp
                    ),
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    )
                )

                OutlinedButton(
                    modifier = Modifier.padding(start = 20.dp),
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

@Composable
@Preview
fun UpsertSpendingCoreScreenPreview(modifier: Modifier = Modifier) {
    UpsertSpendingCoreScreen(
        state = UpsertSpendingState(),
        onAction = {},
        onSaveClick = {}
    )
}