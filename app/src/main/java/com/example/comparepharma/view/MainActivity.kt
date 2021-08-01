package com.example.comparepharma.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.comparepharma.data.Pharma
import com.example.comparepharma.repository.RepositorySingle
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

        val button = findViewById<MaterialButton>(binding.buttonSearch.id)

        button.setOnClickListener { it ->
            val data: List<Pharma> = RepositorySingle.getPharmas()

            for (i in data.indices) {
                when (data[i].name) {
                    PARACETAMOL -> {
                        Toast.makeText(
                            this@MainActivity,
                            data[i].name + " №" + i,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> {
                        Toast.makeText(
                            this@MainActivity,
                            data[i].name,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, MainFragment.newInstance())
                .commitNow()
        }
    }
}




