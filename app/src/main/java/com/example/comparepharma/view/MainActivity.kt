package com.example.comparepharma.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.comparepharma.R
import com.example.comparepharma.databinding.MainActivityBinding
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
    }
}
