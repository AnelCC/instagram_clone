package com.anelcc.instagram.main

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.anelcc.instagram.IgViewModel

@Composable
fun NotificationMessage(viewModel: IgViewModel) {
    val notificationState = viewModel.popNotification.value
    val notificationMessage = notificationState?.getContentOrNull()

    if (!notificationMessage.isNullOrBlank()){
        Toast.makeText(LocalContext.current, notificationMessage, Toast.LENGTH_LONG).show()
    }
}