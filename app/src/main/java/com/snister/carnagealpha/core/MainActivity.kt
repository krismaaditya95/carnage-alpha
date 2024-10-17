package com.snister.carnagealpha.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.snister.carnagealpha.config.ScreenRoutes
import com.snister.carnagealpha.core.presentation.shared.BalanceCard
import com.snister.carnagealpha.core.presentation.shared.MainMenu
import com.snister.carnagealpha.core.presentation.shared.SpendingHighlights
import com.snister.carnagealpha.core.presentation.shared.TopBar
import com.snister.carnagealpha.features.expense_tracker.presentation.dashboard_overview.DashboardOverviewCoreScreen
import com.snister.carnagealpha.features.expense_tracker.presentation.dashboard_overview.DashboardOverviewScreen
import com.snister.carnagealpha.features.expense_tracker.presentation.dashboard_overview.DashboardOverviewState
import com.snister.carnagealpha.features.expense_tracker.presentation.upsert_balance.UpsertBalanceScreen
import com.snister.carnagealpha.ui.theme.CarnageAlphaTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            CarnageAlphaTheme {
//                val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
//                    state = rememberTopAppBarState()
//                )
                Navigation(modifier = Modifier.fillMaxSize())

//                Scaffold(
//                    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
//                    topBar = {
//                        TopBar(
//                            scrollBehavior = scrollBehavior,
//                            modifier = Modifier.fillMaxWidth()
//                        )
//                    }
//                ) { innerPadding ->
//                    MainScreen(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(innerPadding)
//                    )
//                }
            }
        }
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MainScreen(modifier: Modifier = Modifier) {
//
//    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
//        state = rememberTopAppBarState()
//    )
//
//    Scaffold(
//        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
//        topBar = {
//            TopBar(
//                scrollBehavior = scrollBehavior,
//                modifier = Modifier.fillMaxWidth()
//            )
//        }
//    ) { innerPadding ->
//        Column(
//            modifier = modifier
//                .verticalScroll(rememberScrollState())
//                .padding(innerPadding)
//                .fillMaxSize()
//        ){
//            Spacer(modifier = Modifier.height(30.dp))
//            BalanceCard(
//                modifier = Modifier.fillMaxWidth()
//            )
//            Spacer(modifier = Modifier.height(4.dp))
//            MainMenu(
//                modifier = Modifier.fillMaxWidth(),
//                onClick = {}
//            )
//            Spacer(modifier = Modifier.height(10.dp))
//            SpendingHighlights(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(20.dp)
//            )
//        }
//    }
//}

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = ScreenRoutes.SpendingOverview,
//        enterTransition = EnterTransition.None,
//        exitTransition = ExitTransition.None
        ){

        composable<ScreenRoutes.SpendingOverview>{
            DashboardOverviewScreen(
                onBalanceClick = {
                    navController.navigate(ScreenRoutes.Balance)
                },
                onAddSpendingClick = {
                    navController.navigate(ScreenRoutes.SpendingDetails)
                }
            )
        }

        composable<ScreenRoutes.SpendingDetails> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(text = "Spending Details")
            }
        }

        composable<ScreenRoutes.Balance> (
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ){
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
        onDeleteSpending = {}
    )
}