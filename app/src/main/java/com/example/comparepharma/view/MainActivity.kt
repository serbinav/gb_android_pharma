package com.example.comparepharma.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.comparepharma.R
import com.google.android.material.button.MaterialButton
import com.example.comparepharma.databinding.MainActivityBinding
import java.util.*

const val PARACETAMOL: String = "ПАРАЦЕТАМОЛ"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val button: MaterialButton = binding.buttonSearch

        button.setOnClickListener { it ->
            Toast.makeText(
                this@MainActivity,
                R.string.loading_text,
                Toast.LENGTH_SHORT
            ).show()
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, MainFragment.newInstance())
                .commitNow()
        }
    }
}
