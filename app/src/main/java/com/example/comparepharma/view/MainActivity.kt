package com.example.comparepharma.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.example.comparepharma.databinding.MainActivityBinding
import com.example.comparepharma.model.data.Cost
import com.example.comparepharma.model.repository.RepositorySingleImpl
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
            val data: List<Cost> = RepositorySingleImpl.getPharmaFromLocal()

            for (i in data.indices) {
                when (data[i].medicament.name) {
                    PARACETAMOL -> {
                        Toast.makeText(
                            this@MainActivity,
                            data[i].medicament.name + " №" + i,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> {
                        Toast.makeText(
                            this@MainActivity,
                            data[i].medicament.name,
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
