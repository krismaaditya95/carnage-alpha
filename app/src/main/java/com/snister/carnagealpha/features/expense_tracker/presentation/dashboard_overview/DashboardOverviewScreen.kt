package com.snister.carnagealpha.features.expense_tracker.presentation.dashboard_overview

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import com.snister.carnagealpha.core.openAppSettings
import com.snister.carnagealpha.core.presentation.shared.BalanceCardV3
import com.snister.carnagealpha.core.presentation.shared.CameraPermissionTextProvider
import com.snister.carnagealpha.core.presentation.shared.CarnageButton
import com.snister.carnagealpha.core.presentation.shared.DashboardSection
import com.snister.carnagealpha.core.presentation.shared.MainMenuV3
import com.snister.carnagealpha.core.presentation.shared.NotificationPermissionTextProvider
import com.snister.carnagealpha.core.presentation.shared.PermissionDialog
import com.snister.carnagealpha.core.presentation.shared.RecordAudioPermissionTextProvider
import com.snister.carnagealpha.core.presentation.shared.TopBar
import com.snister.carnagealpha.core.presentation.shared.TransactionItemWidget
import com.snister.carnagealpha.core.presentation.shared.TransactionType
import com.snister.carnagealpha.features.expense_tracker.presentation.dashboard_overview.DashboardOverviewViewModel.Companion.CAMERA_PERMISSION

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun DashboardOverviewScreen(
    viewModel: DashboardOverviewViewModel,
    onBalanceClick: () -> Unit,
    onAddSpendingClick: () -> Unit,
    onIncomeOverviewClick: () -> Unit,
    onChangeSourceLedgerClick: () -> Unit
) {
    LaunchedEffect(key1 = true) {
        viewModel.onAction(DashboardOverviewAction.LoadSpendingOverviewAndBalance)
        //viewModel.onAction(DashboardOverviewAction.LoadSourceLedgerList)
        //viewModel.onAction(DashboardOverviewAction.LoadSourceLedgerById)
    }

    DashboardOverviewCoreScreen(
        state = viewModel.state,
        onAction = viewModel::onAction,
        onBalanceClick = onBalanceClick,
        onAddSpendingClick = onAddSpendingClick,
        onIncomeOverviewClick = onIncomeOverviewClick,
        onDeleteSpending = {
            viewModel.onAction(DashboardOverviewAction.OnDeleteSpending(it))
        },
        onChangeSourceLedgerClick = onChangeSourceLedgerClick,
        dialogQueue = viewModel.visiblePermissionDialogQueue,
        viewModel = viewModel
    )
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardOverviewCoreScreen(
    modifier: Modifier = Modifier,
    state: DashboardOverviewState,
    onAction: (DashboardOverviewAction) -> Unit,
    onBalanceClick: () -> Unit,
    onAddSpendingClick: () -> Unit,
    onIncomeOverviewClick: () -> Unit,
    onDeleteSpending: (Int) -> Unit,
    onChangeSourceLedgerClick: () -> Unit,
    dialogQueue: List<String>? = null,
    viewModel: DashboardOverviewViewModel? = null
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = rememberTopAppBarState()
    )

    val currentActivity = LocalContext.current as Activity

    val notificationPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            viewModel?.onPermissionResult(
                permission = Manifest.permission.POST_NOTIFICATIONS,
                isGranted = isGranted
            )
        }
    )

    val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            viewModel?.permissionsToRequest?.forEach { permission ->
                viewModel.onPermissionResult(
                    permission = permission,
                    isGranted = permissions[permission] == true
                )
            }
        }
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBar(
                scrollBehavior = scrollBehavior,
                modifier = Modifier.fillMaxWidth(),
                onNotificationClick = {
                    notificationPermissionResultLauncher.launch(
                        Manifest.permission.POST_NOTIFICATIONS
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
//                .border(1.dp, cmykGreen)
        ){
            BalanceCardV3(
//                modifier = Modifier.fillMaxWidth(),
                onBalanceClick = onBalanceClick,
                balance = state.currentSourceLedger.sourceLedgerBalance,
                sourceLedgerName = state.currentSourceLedger.sourceLedgerName,
                onChangeSourceLedgerClick = onChangeSourceLedgerClick
            )
            Spacer(modifier = Modifier.height(14.dp))
            CarnageButton(
                modifier = Modifier.padding(start = 20.dp),
                buttonTitle = "Request Multiple Permission",
                onClick = {
                    // METHOD 1 =======================================================
//                    if(!viewModel!!.arePermissionGranted(currentActivity)){
//
//                        Log.d("ARE PERMISSION GRANTED = ", viewModel.arePermissionGranted(currentActivity).toString())
//                        Toast.makeText(
//                            currentActivity, "CAMERA and RECORD AUDIO permission is NOT GRANTED, You CAN'T use this feature!", Toast.LENGTH_LONG
//                        ).show()
//                        ActivityCompat.requestPermissions(currentActivity, CAMERA_PERMISSION, 10)
//                    }else{
//                        Log.d("[MASUK KE ELSE] ARE PERMISSION GRANTED = ", viewModel.arePermissionGranted(currentActivity).toString())
//                        Toast.makeText(
//                            currentActivity, "CAMERA and RECORD AUDIO permission is GRANTED, Continue use the feature...", Toast.LENGTH_LONG
//                        ).show()
//                        //navController.navigate(ScreenRoutes.CameraScreen)
//                    }
                    // END OF METHOD 1 =================================================


                    // METHOD 2 =======================================================
//                    if(ContextCompat.checkSelfPermission(currentActivity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
//                        ContextCompat.checkSelfPermission(currentActivity, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
//                        // USE THE FEATURE THAT REQUIRES THE PERMISSION
//                        Toast.makeText(
//                            currentActivity, "CAMERA and RECORD AUDIO permission is GRANTED, Continue use the feature...", Toast.LENGTH_LONG
//                        ).show()
//                    }else{
//                        Toast.makeText(
//                            currentActivity, "CAMERA and RECORD AUDIO permission is NOT GRANTED, You CAN'T use this feature!", Toast.LENGTH_LONG
//                        ).show()
//                        multiplePermissionResultLauncher.launch(viewModel!!.permissionsToRequest)
//                    }
                    // END OF METHOD 2 =================================================
//                    multiplePermissionResultLauncher.launch(viewModel!!.permissionsToRequest)


                    // METHOD 3 (COMBINATION OF METHOD 1 & 2) =======================================================
                    if(viewModel!!.arePermissionGranted(currentActivity)){
                        Log.d("[MASUK KE ELSE] ARE PERMISSION GRANTED = ", viewModel.arePermissionGranted(currentActivity).toString())
                        Toast.makeText(
                            currentActivity, "CAMERA and RECORD AUDIO permission is GRANTED, Continue use the feature...", Toast.LENGTH_LONG
                        ).show()
                    }else{
                        Log.d("ARE PERMISSION GRANTED = ", viewModel.arePermissionGranted(currentActivity).toString())
                        Toast.makeText(
                            currentActivity, "CAMERA and RECORD AUDIO permission is NOT GRANTED, You CAN'T use this feature!", Toast.LENGTH_LONG
                        ).show()
                        multiplePermissionResultLauncher.launch(viewModel.permissionsToRequest)
                    }
                    // END OF METHOD 3 =================================================
                }
            )

            Spacer(modifier = Modifier.height(14.dp))

            dialogQueue?.reversed()
                ?.forEach { permission ->
                    PermissionDialog(
                        permissionTextProvider = when(permission){
                            Manifest.permission.POST_NOTIFICATIONS -> {
                                NotificationPermissionTextProvider()
                            }
                            Manifest.permission.CAMERA -> {
                                CameraPermissionTextProvider()
                            }
                            Manifest.permission.RECORD_AUDIO -> {
                                RecordAudioPermissionTextProvider()
                            }
//                            Manifest.permission.CALL_PHONE -> {
//                                CallPhonePermissionTextProvider()
//                            }
                            else -> return@forEach
                        },
                        isDeclined = !shouldShowRequestPermissionRationale(currentActivity, permission),
                        onDismiss = viewModel!!::dismissDialog,
                        onGrantedClick = {
                            viewModel.dismissDialog()
                            multiplePermissionResultLauncher.launch(
                                arrayOf(permission)
                            )
                        },
                        onGoToAppSettingsClick = currentActivity::openAppSettings
                    )
                }
            MainMenuV3(
                modifier = Modifier.fillMaxWidth(),
                onAddSpendingClick = {onAddSpendingClick()},
                onAddIncomeClick = {onIncomeOverviewClick()}
            )
            Spacer(modifier = Modifier.height(10.dp))

//            LazyColumn(
//                modifier = modifier
//                    .fillMaxWidth()
//                    .height(160.dp)
//                    .padding(start = 20.dp, end = 20.dp),
////                    .border(1.dp, cmykRed),
//                contentPadding = PaddingValues(top = 14.dp),
//                verticalArrangement = Arrangement.spacedBy(14.dp)
//            ) {
//                itemsIndexed(state.incomeList){
//                        index, item ->
//                    TransactionItemWidget(
//                        transactionType = TransactionType.Income,
//                        sourceName = item.incomeSourceName,
//                        transactionTimeStamp = item.dateTime,
//                        nominal = item.incomeAmount,
//                        onDeleteIncome = { }
//                    )
//                }
//            }
//            SpendingHighlights(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(20.dp)
//            )

            DashboardSection(
                sectionTitle = "Recent Transactions",
                onSeeAllClick = {

                },
                content = {
//                    Column {
//                        TransactionItemWidget(
//                            transactionType = TransactionType.Income,
//                            sourceName = "Gaji bulan ini",
//                            transactionTimeStamp = ZonedDateTime.now(),
//                            nominal = 4000000,
//                            onDeleteIncome = { }
//                        )
//                        TransactionItemWidget(
//                            transactionType = TransactionType.Income,
//                            sourceName = "Gaji bulan ini",
//                            transactionTimeStamp = ZonedDateTime.now(),
//                            nominal = 4000000,
//                            onDeleteIncome = { }
//                        )
//                    }

                    LazyColumn(
                        modifier = modifier
                            .fillMaxWidth()
                            .height(400.dp),
//                            .padding(start = 20.dp, end = 20.dp),
                        contentPadding = PaddingValues(top = 14.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        itemsIndexed(state.incomeList){
                                index, item ->
                            TransactionItemWidget(
                                transactionType = TransactionType.Income,
                                sourceName = item.incomeSourceName,
                                transactionTimeStamp = item.dateTime,
                                nominal = item.incomeAmount,
                                onDeleteIncome = { }
                            )
                        }
                    }
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(showBackground = true)
@Composable
fun DashboardOverviewScreenPreview(modifier: Modifier = Modifier) {
    DashboardOverviewCoreScreen(
        state = DashboardOverviewState(),
        onAction = {},
        onBalanceClick = {},
        onAddSpendingClick = {},
        onIncomeOverviewClick = {},
        onDeleteSpending = {},
        onChangeSourceLedgerClick = {}
    )
}