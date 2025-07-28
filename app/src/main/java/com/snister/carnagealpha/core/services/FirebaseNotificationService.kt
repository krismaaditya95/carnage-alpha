package com.snister.carnagealpha.core.services

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.snister.carnagealpha.R
import com.snister.carnagealpha.core.MainActivity
import kotlin.random.Random

class FirebaseNotificationService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FirebaseNotificationService.kt | onNewToken ==> ", "The new token is : $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        message.notification?.let {
            showNotification(it)
        }

        if (message.data.isNotEmpty()) {
            handleDataMessage()
        }
    }

    private fun handleDataMessage(){
        Log.d("FirebaseNotificationService.kt | handleDataMessage ==> ", "-")
    }

    private fun showNotification(message: RemoteMessage.Notification){

        val channelId = "DefaultNotificationChannel"
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channelName = "FCM_00"

        val intent = Intent(this, MainActivity::class.java).apply{
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(message.title)
            .setContentText(message.body)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

//        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
//            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
//            notificationManager.createNotificationChannel(channel)
//        }

        notificationManager.notify(Random.nextInt(), notificationBuilder)
    }



}