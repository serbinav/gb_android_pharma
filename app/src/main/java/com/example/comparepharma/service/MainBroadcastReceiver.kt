package com.example.comparepharma.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.comparepharma.R
import com.example.comparepharma.view.showToast

class MainBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        context.getString(R.string.broadcast_receiver_text).showToast(context, Toast.LENGTH_LONG)
    }
}