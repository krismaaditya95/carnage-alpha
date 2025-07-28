package com.snister.carnagealpha.core.presentation.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snister.carnagealpha.R
import com.snister.carnagealpha.ui.theme.cEEEEEE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionDialog(
    permissionTextProvider: PermissionTextProvider? = null,
    isDeclined: Boolean,
    onDismiss: () -> Unit,
    onGrantedClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit
) {
    BasicAlertDialog(
        modifier = Modifier.background(cEEEEEE),
        onDismissRequest = onDismiss,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 12.dp)
            ) {
                if (permissionTextProvider != null) {
                    Text(
                        text = permissionTextProvider.getDescription(isDeclined = isDeclined),
                        fontFamily = Font(R.font.roboto_regular).toFontFamily(),
                        fontSize = 14.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                HorizontalDivider()
                Text(
                    text = if(isDeclined){
                        "Go To App Settings"
                    }else{
                        "Grant Permission"
                    },
                    fontFamily = Font(R.font.roboto_regular).toFontFamily(),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if(isDeclined){
                                onGoToAppSettingsClick()
                            }else{
                                onGrantedClick()
                            }
                        }
                        .padding(16.dp)
                )


            }
        }
    )
}

@Composable
@Preview(showBackground = false)
fun PermissionDialogPreview(){
    PermissionDialog(
        isDeclined = true,
        onDismiss = {},
        onGrantedClick = {}
    ) { }
}


interface PermissionTextProvider{
    fun getDescription(isDeclined: Boolean): String
}

class NotificationPermissionTextProvider: PermissionTextProvider{
    override fun getDescription(isDeclined: Boolean): String {
        return if(isDeclined){
            "You permanently declined the NOTIFICATION permission. " +
                    "Now You have to go to app settings to grant the permission."
        }else{
            "This App needs to access the NOTIFICATION permission to show NOTIFICATION, Hermano."
        }
    }
}

class CameraPermissionTextProvider: PermissionTextProvider{
    override fun getDescription(isDeclined: Boolean): String {
        return if(isDeclined){
            "You permanently declined the CAMERA permission. " +
                    "Now You have to go to app settings to grant the permission."
        }else{
            "This App needs to access the CAMERA permission to use CAMERA features, Hermano."
        }
    }
}

class RecordAudioPermissionTextProvider: PermissionTextProvider{
    override fun getDescription(isDeclined: Boolean): String {
        return if(isDeclined){
            "You permanently declined the RECORD_AUDIO permission. " +
                    "Now You have to go to app settings to grant the permission."
        }else{
            "This App needs to access the RECORD_AUDIO permission to be able record audio, Hermano."
        }
    }
}

class CallPhonePermissionTextProvider: PermissionTextProvider{
    override fun getDescription(isDeclined: Boolean): String {
        return if(isDeclined){
            "You permanently declined the PHONE CALL permission. " +
                    "Now You have to go to app settings to grant the permission."
        }else{
            "This App needs to access the PHONE CALL permission to be able to make a call, Hermano."
        }
    }
}