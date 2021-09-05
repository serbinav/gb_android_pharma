package com.example.comparepharma.view

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import com.example.comparepharma.R
import com.example.comparepharma.databinding.MainActivityBinding
import com.example.comparepharma.utils.Constants
import com.example.comparepharma.utils.showToast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSearch.setOnClickListener {
            with(binding.search) {
                if (visibility == View.VISIBLE) {
                    getString(R.string.loading_text).showToast(this@MainActivity)
                    visibility = View.GONE
                } else {
                    visibility = View.VISIBLE
                }
            }
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, MainFragment.newInstance())
                .commitNow()
        }

        binding.buttonFavorites.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, FavoritesFragment.newInstance())
                .addToBackStack("")
                .commitAllowingStateLoss()
        }

        binding.buttonPhoneBook.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, PhoneBookFragment.newInstance())
                .addToBackStack("")
                .commitAllowingStateLoss()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            createNotificationChannel(notificationManager)
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(
                    Constants.FIREBASE_TOKEN,
                    "Fetching FCM registration token failed",
                    task.exception
                )
                return@OnCompleteListener
            }
            val token = task.result
            token?.let {
                Log.d(Constants.FIREBASE_TOKEN, token)
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channelName = "Channel Name"
        val descriptionText = "Channel Description"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(Constants.CHANNEL_ID, channelName, importance).apply {
            description = descriptionText
        }
        notificationManager.createNotificationChannel(channel)
    }
}
