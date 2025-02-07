package com.example.citasmedicas.ui.fragments

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.citasmedicas.R

class NotificacionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        mostrarNotificacion(context)
    }

    @SuppressLint("ServiceCast")
    private fun mostrarNotificacion(context: Context) {
        val channelId = "cita_channel"
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Recordatorio de Citas",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Recordatorio de Cita")
            .setContentText("Tienes una cita en 1 hora")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(1, notification)
    }
}