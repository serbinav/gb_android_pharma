package com.example.comparepharma.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.widget.Toast
import com.example.comparepharma.R
import com.example.comparepharma.utils.showToast

class MainBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (Settings.Global.getInt(
                context.contentResolver,
                Settings.Global.AIRPLANE_MODE_ON,
                0
            ) != 0
        ) {
            context.getString(R.string.broadcast_receiver_airplane_on)
                .showToast(context, Toast.LENGTH_LONG)
        } else {
            context.getString(R.string.broadcast_receiver_airplane_off)
                .showToast(context, Toast.LENGTH_LONG)
        }
    }
}