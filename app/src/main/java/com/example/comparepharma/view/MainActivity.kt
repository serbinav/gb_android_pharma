package com.example.comparepharma.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.comparepharma.R
import com.google.android.material.button.MaterialButton
import com.example.comparepharma.databinding.MainActivityBinding
import kotlinx.android.synthetic.main.main_details_fragment.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val button: MaterialButton = binding.buttonSearch

        button.setOnClickListener {
            getString(R.string.loading_text).showToast(this@MainActivity)
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
