package com.snister.carnagealpha.core

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.snister.carnagealpha.R
import com.snister.carnagealpha.config.ScreenRoutes
import com.snister.carnagealpha.core.presentation.MainActivityAction
import com.snister.carnagealpha.core.presentation.MainActivityEvents
import com.snister.carnagealpha.core.presentation.MainActivityState
import com.snister.carnagealpha.core.presentation.MainActivityViewModel
import com.snister.carnagealpha.core.presentation.shared.CustomBottomNav
import com.snister.carnagealpha.features.expense_tracker.presentation.dashboard_overview.DashboardOverviewScreen
import com.snister.carnagealpha.features.expense_tracker.presentation.income_overview.IncomeOverviewScreen
import com.snister.carnagealpha.features.expense_tracker.presentation.spending_overview.SpendingOverviewAction
import com.snister.carnagealpha.features.expense_tracker.presentation.spending_overview.SpendingOverviewScreen
import com.snister.carnagealpha.features.expense_tracker.presentation.upsert_income.UpsertBalanceScreen
import com.snister.carnagealpha.features.expense_tracker.presentation.upsert_spending.UpsertSpendingScreen
import com.snister.carnagealpha.ui.theme.CarnageAlphaTheme
import com.snister.carnagealpha.ui.theme.cDC5F00
import com.snister.carnagealpha.ui.theme.cmykGreen
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            CarnageAlphaTheme {
                val navController = rememberNavController()
                MainActivityCoreScreen(
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun MainActivityCoreScreen(
    navController: NavHostController,
    viewModel: MainActivityViewModel = koinViewModel(),
    state: MainActivityState = viewModel.state,
    onAction: (MainActivityAction) -> Unit = viewModel::onAction
) {

    val currentContext = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.event.collect { event ->
            when(event){
                MainActivityEvents.UpsertSourceLedgerFailed -> {
                    Toast.makeText(
                        currentContext, "ERROR insert initial source ledger!", Toast.LENGTH_LONG
                    ).show()
                }
                MainActivityEvents.UpsertSourceLedgerSuccess -> {
                    Toast.makeText(
                        currentContext, "Successfully inserted initial source ledger!", Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    Scaffold(
        bottomBar = {
            CustomBottomNav(navController = navController)
        }
    ) { paddingValues ->

        Navigation(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateTopPadding()),
            navController = navController,
            state = state,
            onAction = onAction
        )

        when{
            state.isChangeSourceLedgerDialogVisible -> {
                ChangeSourceLedgerBottomSheetDialog(
                    state = state,
                    onAction = onAction
                )
            }
        }
    }
}

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    state: MainActivityState,
    onAction: (MainActivityAction) -> Unit
) {


    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = ScreenRoutes.DashboardOverview,
        enterTransition = { slideInHorizontally { it } },
        popEnterTransition = { slideInHorizontally { -it } },
        exitTransition = { slideOutHorizontally { -it } },
        popExitTransition = { slideOutHorizontally { -it } },

        ){

        composable<ScreenRoutes.DashboardOverview>{
            DashboardOverviewScreen(
                onBalanceClick = {
                    navController.navigate(ScreenRoutes.Balance)
                },
                onAddSpendingClick = {
                    navController.navigate(ScreenRoutes.SpendingOverview)
                },
                onIncomeOverviewClick = {
                    navController.navigate(ScreenRoutes.IncomeOverview)
                },
                onChangeSourceLedgerClick = {
                    onAction(MainActivityAction.ShowChangeSourceLedgerDialog)
                }
            )
        }

        composable<ScreenRoutes.SpendingOverview>{
            SpendingOverviewScreen(
                onBalanceClick = {
                    navController.navigate(ScreenRoutes.Balance)
                },
                onAddSpendingClick = {
                    navController.navigate(ScreenRoutes.SpendingDetails(-1))
                }
            )
        }

        composable<ScreenRoutes.IncomeOverview>{
            IncomeOverviewScreen(
                onBalanceClick = {
                    navController.navigate(ScreenRoutes.Balance)
                },
                onAddIncomeClick = {
//                    navController.navigate(ScreenRoutes.IncomeDetails(-1))
                    navController.navigate(ScreenRoutes.Balance)
                }
            )
        }

        composable<ScreenRoutes.SpendingDetails> {
            UpsertSpendingScreen(
                onSaveClick = {
                    navController.popBackStack()
                }
            )
        }

        composable<ScreenRoutes.Balance> {
            UpsertBalanceScreen(
                onSaveClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeSourceLedgerBottomSheetDialog(
    modifier: Modifier = Modifier,
    state: MainActivityState,
    onAction: (MainActivityAction) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val sheetScope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = {
            sheetScope.launch {
                sheetState.hide()
            }.invokeOnCompletion {
                if(!sheetState.isVisible){
                    onAction(MainActivityAction.HideChangeSourceLedgerDialog)
                }
            }
        },
//        modifier = Modifier
//            .padding(bottom = 20.dp),
        sheetState = sheetState,
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
        ) {
            Text(
                text = "Change Ledger",
                color = cDC5F00.copy(0.6f),
                fontFamily = Font(R.font.roboto_regular).toFontFamily(),
                fontSize = 20.sp,
                modifier = Modifier
                    .border(1.dp, cmykGreen)
            )
            HorizontalDivider(
                modifier = Modifier
                    .padding(10.dp)
            )
            // LOAD LIST OF SOURCE LEDGER HERE
            // ...


            Row(
                modifier = modifier.align(Alignment.End),
            ) {
                TextButton(
                    onClick = {
                        sheetScope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            if(!sheetState.isVisible){
                                onAction(MainActivityAction.HideChangeSourceLedgerDialog)
                            }
                        }
                    }
                ) {
                    Text(text = "CANCEL")
                }

                TextButton(
                    onClick = {
                        onAction(
                            MainActivityAction.OnSourceLedgerChanged(
                                selectedSourceLedgerId = 1
                            )
                        )

                        sheetScope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            if(!sheetState.isVisible){
                                onAction(MainActivityAction.HideChangeSourceLedgerDialog)
                            }
                        }
                    }
                ) {
                    Text(text = "SAVE")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
//    DashboardOverviewCoreScreen(
//        state = DashboardOverviewState(),
//        onAction = {},
//        onBalanceClick = {},
//        onAddSpendingClick = {},
//        onIncomeOverviewClick = {},
//        onDeleteSpending = {}
//    )

    MainActivityCoreScreen(
        navController = rememberNavController()
    )
}