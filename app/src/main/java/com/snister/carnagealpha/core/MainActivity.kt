package com.snister.carnagealpha.core

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
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
import com.snister.carnagealpha.core.presentation.shared.CallPhonePermissionTextProvider
import com.snister.carnagealpha.core.presentation.shared.CameraPermissionTextProvider
import com.snister.carnagealpha.core.presentation.shared.CustomBottomNav
import com.snister.carnagealpha.core.presentation.shared.NotificationPermissionTextProvider
import com.snister.carnagealpha.core.presentation.shared.PermissionDialog
import com.snister.carnagealpha.core.presentation.shared.RecordAudioPermissionTextProvider
import com.snister.carnagealpha.features.expense_tracker.presentation.dashboard_overview.DashboardOverviewAction
import com.snister.carnagealpha.features.expense_tracker.presentation.dashboard_overview.DashboardOverviewScreen
import com.snister.carnagealpha.features.expense_tracker.presentation.dashboard_overview.DashboardOverviewViewModel
import com.snister.carnagealpha.features.expense_tracker.presentation.income_overview.IncomeOverviewAction
import com.snister.carnagealpha.features.expense_tracker.presentation.income_overview.IncomeOverviewScreen
import com.snister.carnagealpha.features.expense_tracker.presentation.income_overview.IncomeOverviewViewModel
import com.snister.carnagealpha.features.expense_tracker.presentation.shared_widgets.SourceLedgerListWidget
import com.snister.carnagealpha.features.expense_tracker.presentation.spending_overview.SpendingOverviewAction
import com.snister.carnagealpha.features.expense_tracker.presentation.spending_overview.SpendingOverviewScreen
import com.snister.carnagealpha.features.expense_tracker.presentation.spending_overview.SpendingOverviewViewModel
import com.snister.carnagealpha.features.expense_tracker.presentation.upsert_income.UpsertBalanceScreen
import com.snister.carnagealpha.features.expense_tracker.presentation.upsert_spending.UpsertSpendingScreen
import com.snister.carnagealpha.ui.theme.CarnageAlphaTheme
import com.snister.carnagealpha.ui.theme.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
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

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun MainActivityCoreScreen(
    navController: NavHostController,
    mainActivityViewModel: MainActivityViewModel = koinViewModel(),
    dashboardOverviewViewModel: DashboardOverviewViewModel = koinViewModel(),
    spendingOverviewModel: SpendingOverviewViewModel = koinViewModel(),
    incomeOverviewModel: IncomeOverviewViewModel = koinViewModel(),

    mainActivityState: MainActivityState = mainActivityViewModel.state,
    mainActivityOnAction: (MainActivityAction) -> Unit = mainActivityViewModel::onAction
) {

    val currentContext = LocalContext.current
    val currentActivity = LocalContext.current as Activity

    val notificationPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            mainActivityViewModel.onPermissionResult(
                permission = Manifest.permission.POST_NOTIFICATIONS,
                isGranted = isGranted
            )
        }
    )

    LaunchedEffect(key1 = true) {
        val isPermissionGranted = mainActivityViewModel.arePermissionGranted(currentActivity)

        if(isPermissionGranted){
            Log.d("[LOG.D] debug :)", "[MainActivity] is Permission Granted = $isPermissionGranted" )
            Toast.makeText(
                currentActivity, "NOTIFICATION permission is GRANTED, Continue use the feature...", Toast.LENGTH_LONG
            ).show()
            mainActivityViewModel.retrieveFCMToken()
        }else{
            Log.d("[LOG.D] debug :)", "[MainActivity] is Permission Granted = $isPermissionGranted")
            Toast.makeText(
                currentActivity, "NOTIFICATION permission is NOT GRANTED, You CAN'T use this feature!", Toast.LENGTH_LONG
            ).show()

            notificationPermissionResultLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        mainActivityViewModel.event.collect { event ->
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
            mainActivityState = mainActivityState,
            mainActivityOnAction = mainActivityOnAction,
            dashboardOverviewViewModel = dashboardOverviewViewModel,
            spendingOverviewModel = spendingOverviewModel,
            incomeOverviewModel = incomeOverviewModel
        )

        when{
            mainActivityState.isChangeSourceLedgerDialogVisible -> {
                ChangeSourceLedgerBottomSheetDialog(
                    mainActivityState = mainActivityState,
                    mainActivityOnAction = mainActivityOnAction,
                    mainActivityViewModel = mainActivityViewModel,

                    dashboardOverviewOnAction = dashboardOverviewViewModel::onAction,
                    spendingOverviewOnAction = spendingOverviewModel::onAction,
                    incomeOverviewOnAction = incomeOverviewModel::onAction
                )
            }
        }

        mainActivityViewModel.visiblePermissionDialogQueue
            .forEach { permission ->
                PermissionDialog(
                    permissionTextProvider = when (permission) {
                        Manifest.permission.POST_NOTIFICATIONS -> {
                            NotificationPermissionTextProvider()
                        }

                        Manifest.permission.CAMERA -> {
                            CameraPermissionTextProvider()
                        }

                        Manifest.permission.RECORD_AUDIO -> {
                            RecordAudioPermissionTextProvider()
                        }

                        Manifest.permission.CALL_PHONE -> {
                            CallPhonePermissionTextProvider()
                        }

                        else -> return@forEach
                    },
                    isDeclined = !shouldShowRequestPermissionRationale(currentActivity, permission),
                    onDismiss = mainActivityViewModel::dismissDialog,
                    onGrantedClick = {
                        mainActivityViewModel.dismissDialog()
                    },
                    onGoToAppSettingsClick = currentActivity::openAppSettings
                )
            }
    }
}

fun Activity.openAppSettings(){
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    mainActivityState: MainActivityState,
    mainActivityOnAction: (MainActivityAction) -> Unit,
    dashboardOverviewViewModel: DashboardOverviewViewModel,
    spendingOverviewModel: SpendingOverviewViewModel,
    incomeOverviewModel: IncomeOverviewViewModel
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
                viewModel = dashboardOverviewViewModel,
                onBalanceClick = {
                    navController.navigate(ScreenRoutes.Balance){
                        launchSingleTop = true
                    }
                },
                onAddSpendingClick = {
                    navController.navigate(ScreenRoutes.SpendingOverview){
                        launchSingleTop = true
                    }
                },
                onIncomeOverviewClick = {
                    navController.navigate(ScreenRoutes.IncomeOverview){
                        launchSingleTop = true
                    }
                },
                onChangeSourceLedgerClick = {
                    mainActivityOnAction(MainActivityAction.ShowChangeSourceLedgerDialog)
                }
            )
        }

        composable<ScreenRoutes.SpendingOverview>{
            SpendingOverviewScreen(
                viewModel = spendingOverviewModel,
                onBalanceClick = {
                    navController.navigate(ScreenRoutes.Balance){
                        launchSingleTop = true
                    }
                },
                onAddSpendingClick = {
                    navController.navigate(ScreenRoutes.SpendingDetails(-1)){
                        launchSingleTop = true
                    }
                },
                onChangeSourceLedgerClick = {
                    mainActivityOnAction(MainActivityAction.ShowChangeSourceLedgerDialog)
                }
            )
        }

        composable<ScreenRoutes.IncomeOverview>{
            IncomeOverviewScreen(
                viewModel = incomeOverviewModel,
                onBalanceClick = {
                    navController.navigate(ScreenRoutes.Balance){
                        launchSingleTop = true
                    }
                },
                onAddIncomeClick = {
//                    navController.navigate(ScreenRoutes.IncomeDetails(-1))
                    navController.navigate(ScreenRoutes.Balance){
                        launchSingleTop = true
                    }
                },
                onChangeSourceLedgerClick = {
                    mainActivityOnAction(MainActivityAction.ShowChangeSourceLedgerDialog)
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
    mainActivityState: MainActivityState,
    mainActivityViewModel: MainActivityViewModel,
    mainActivityOnAction: (MainActivityAction) -> Unit,

    // dashboard overview
    dashboardOverviewOnAction: (DashboardOverviewAction) -> Unit,

    // spending overview
    spendingOverviewOnAction: (SpendingOverviewAction) -> Unit,

    // income overview
    incomeOverviewOnAction: (IncomeOverviewAction) -> Unit
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
                    mainActivityOnAction(MainActivityAction.HideChangeSourceLedgerDialog)
                }
            }
        },
//        modifier = Modifier
//            .padding(bottom = 20.dp),
        sheetState = sheetState,
    ) {
        Column(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
//                .border(1.dp, cC73659)
        ) {
            Text(
                text = "Change Ledger",
                color = cDC5F00.copy(0.6f),
                fontFamily = Font(R.font.roboto_regular).toFontFamily(),
                fontSize = 20.sp,
                modifier = Modifier
//                    .border(1.dp, cmykGreen)
                    .padding(top = 8.dp, bottom = 8.dp)
            )

            // -------------------------
            // FOR TESTING PURPOSE
            // -------------------------
//            Text(
//                text = "[Current Active Ledger ID : ${mainActivityState.currentActiveSourceLedgerId}]",
//                color = cC73659,
//                fontFamily = Font(R.font.roboto_regular).toFontFamily(),
//                fontSize = 16.sp,
//                modifier = Modifier
//                    .padding(top = 8.dp, bottom = 8.dp)
//            )
//            Text(
//                text = "Selected INDEX : ${mainActivityState.selectedSourceLedgerIndexFromList}",
//                color = cDC5F00.copy(0.6f),
//                fontFamily = Font(R.font.roboto_regular).toFontFamily(),
//                fontSize = 20.sp,
//                modifier = Modifier
////                    .border(1.dp, cmykGreen)
//                    .padding(top = 8.dp, bottom = 8.dp)
//            )
//            Text(
//                text = "Selected ID : ${mainActivityState.selectedSourceLedgerIdFromList}",
//                color = cDC5F00.copy(0.6f),
//                fontFamily = Font(R.font.roboto_regular).toFontFamily(),
//                fontSize = 20.sp,
//                modifier = Modifier
////                    .border(1.dp, cmykGreen)
//                    .padding(top = 8.dp, bottom = 8.dp)
//            )
            // -------------------------
            // END OF - FOR TESTING PURPOSE
            // -------------------------

            HorizontalDivider(
                modifier = Modifier
                    .padding(top = 6.dp, bottom = 6.dp)
            )
            // LOAD LIST OF SOURCE LEDGER HERE
            // ...
            when{
                mainActivityState.isLoading ->{
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
//                            .border(1.dp, cDC5F00)
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .width(40.dp)
                                .align(Alignment.Center),
                            color = cC73659
                        )
                    }
                }

                !mainActivityState.isLoading ->{
                    SourceLedgerListWidget(
                        state = mainActivityState,
                        onAction = mainActivityOnAction
                    )
                }
            }

            Row(
                modifier = modifier.align(Alignment.End),
            ) {
                TextButton(
                    onClick = {
                        sheetScope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            if(!sheetState.isVisible){
                                mainActivityOnAction(MainActivityAction.HideChangeSourceLedgerDialog)
                            }
                        }
                    }
                ) {
                    Text(text = "CANCEL")
                }

                TextButton(
                    onClick = {
//                        mainActivityOnAction(MainActivityAction.OnSaveSelectedSourceLedger)
//                        dashboardOverviewOnAction(DashboardOverviewAction.LoadSpendingOverviewAndBalance)
//                        spendingOverviewOnAction(SpendingOverviewAction.LoadSpendingOverviewAndBalance)
//                        incomeOverviewOnAction(IncomeOverviewAction.LoadIncomeOverviewAndBalance)

                        sheetScope.launch(Dispatchers.IO) {
                            sheetState.hide()
                            mainActivityOnAction(MainActivityAction.OnSaveSelectedSourceLedger)
                        }.invokeOnCompletion {
                            if(!sheetState.isVisible){
                                mainActivityOnAction(MainActivityAction.HideChangeSourceLedgerDialog)
                            }

                            dashboardOverviewOnAction(DashboardOverviewAction.LoadSpendingOverviewAndBalance)
                            spendingOverviewOnAction(SpendingOverviewAction.LoadSpendingOverviewAndBalance)
                            incomeOverviewOnAction(IncomeOverviewAction.LoadIncomeOverviewAndBalance)
                        }
                    },
                    enabled = (
                            mainActivityState.selectedSourceLedgerIdFromList != mainActivityState.currentActiveSourceLedgerId)
                ) {
                    Text(text = "SAVE")
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
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