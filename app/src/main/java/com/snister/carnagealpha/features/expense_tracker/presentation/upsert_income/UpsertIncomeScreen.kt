package com.snister.carnagealpha.features.expense_tracker.presentation.upsert_income

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
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
import com.snister.carnagealpha.ui.theme.CarnageAlphaTheme
import com.snister.carnagealpha.ui.theme.*
import org.koin.androidx.compose.koinViewModel

@Composable
fun UpsertBalanceScreen(
    viewModel: UpsertIncomeViewModel = koinViewModel(),
    onSaveClick: () -> Unit
) {
    val currentContext = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.event.collect{ event ->
            when(event){
                UpsertIncomeEvents.UpsertIncomeFailed -> {
                    Toast.makeText(
                        currentContext, "ERROR", Toast.LENGTH_LONG
                    ).show()
                }
                UpsertIncomeEvents.UpsertIncomeSuccess -> {
                    Toast.makeText(
                        currentContext, "SUCCESS", Toast.LENGTH_LONG
                    ).show()
                    onSaveClick()
                }
            }
        }
    }

    UpsertIncomeCoreScreen(
        state = viewModel.state,
        onAction = viewModel::onAction,
        onSaveClick = {
            viewModel.onAction(UpsertIncomeAction.OnIncomeSaved)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpsertIncomeCoreScreen(
    state: UpsertIncomeState,
    onAction: (UpsertIncomeAction) -> Unit,
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
                appBarTitle = "Add Income"
            )
        }
    ){ padding ->
        Box(
            modifier = Modifier
                .padding(padding)
//                .border(1.dp, cmykGreen)
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier
//                    .padding(padding)
            ) {
                Box {
                    ElevatedCard(
                        onClick = {},
                        modifier = Modifier
                            .border(1.dp, cmykGreen, RoundedCornerShape(6.dp))
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)
                            .align(Alignment.CenterStart),
                        shape = RoundedCornerShape(6.dp),
                    ){
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    brush = Brush.linearGradient(
                                        listOf(
                                            cmykGreen,
                                            cEEEEEE
                                        ),
                                        start = Offset(30.0f, 30.0f),
                                        end = Offset(800.0f, 800.0f)
                                    )
                                )
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(20.dp),
                                text = CurrencyFormatter.formatToRupiah(state.tempBalance),
                                fontSize = 28.sp,
                                fontWeight = FontWeight.SemiBold,
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = state.incomeAmountInput,
                    placeholder = {
                        Text(text = state.incomeAmountInput)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    onValueChange = { newValue ->
                        onAction(UpsertIncomeAction.OnIncomeAmountChanged(newValue,))
                    },
                    label = {
                        Text(
                            text = "Income Amount"
                        )
                    },
                    textStyle = TextStyle(
                        fontSize = 18.sp
                    ),
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    )
                )

                OutlinedTextField(
                    value = state.incomeSourceNameInput,
                    placeholder = {
                        Text(text = state.incomeSourceNameInput)
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
//                        .padding(horizontal = 20.dp, vertical = 2.dp),
                    onValueChange = { newValue ->
                        onAction(UpsertIncomeAction.OnIncomeSourceNameChanged(newValue))
                    },
                    label = {
                        Text(
                            text = "Source Name of this Income"
                        )
                    },
                    textStyle = TextStyle(
                        fontSize = 18.sp
                    ),
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    )
                )

//                OutlinedButton(
//                    modifier = Modifier.padding(start = 20.dp),
//                    onClick = {
//                        onSaveClick()
//                    }
//                ) {
//                    Text(
//                        text = "SAVE"
//                    )
//                }

                Button(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .align(Alignment.End)
                        .fillMaxWidth(0.5f)
                        .height(IntrinsicSize.Min),
                    colors = ButtonColors(
                        containerColor = cmykGreen,
                        contentColor = cEEEEEE,
                        disabledContentColor = cEEEEEE,
                        disabledContainerColor = c686D76
                    ),
                    shape = RoundedCornerShape(6.dp),
                    //                    .padding(start = 20.dp),
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
        UpsertIncomeCoreScreen(
            state = UpsertIncomeState(),
            onAction = {  },
            onSaveClick = {

            }
        )
    }
}