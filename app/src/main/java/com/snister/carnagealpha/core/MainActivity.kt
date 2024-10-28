package com.snister.carnagealpha.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.snister.carnagealpha.config.ScreenRoutes
import com.snister.carnagealpha.features.expense_tracker.presentation.dashboard_overview.DashboardOverviewCoreScreen
import com.snister.carnagealpha.features.expense_tracker.presentation.dashboard_overview.DashboardOverviewScreen
import com.snister.carnagealpha.features.expense_tracker.presentation.dashboard_overview.DashboardOverviewState
import com.snister.carnagealpha.features.expense_tracker.presentation.income_overview.IncomeOverviewScreen
import com.snister.carnagealpha.features.expense_tracker.presentation.spending_overview.SpendingOverviewScreen
import com.snister.carnagealpha.features.expense_tracker.presentation.upsert_income.UpsertBalanceScreen
import com.snister.carnagealpha.features.expense_tracker.presentation.upsert_spending.UpsertSpendingScreen
import com.snister.carnagealpha.ui.theme.CarnageAlphaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            CarnageAlphaTheme {
                Navigation(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

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
                    navController.navigate(ScreenRoutes.IncomeDetails(-1))
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

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    DashboardOverviewCoreScreen(
        state = DashboardOverviewState(),
        onAction = {},
        onBalanceClick = {},
        onAddSpendingClick = {},
        onIncomeOverviewClick = {},
        onDeleteSpending = {}
    )
}